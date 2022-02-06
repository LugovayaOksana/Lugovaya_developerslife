package com.example.developerslife.data.di

import android.content.Context
import androidx.room.Room
import com.example.developerslife.BuildConfig
import com.example.developerslife.common.Constants
import com.example.developerslife.data.data_source.PostDataBase
import com.example.developerslife.data.remote.PostApi
import com.example.developerslife.data.repository.RandomRepositoryImpl
import com.example.developerslife.domain.repository.RandomRepository
import com.example.developerslife.domain.use_case.GetCachedPostsUseCase
import com.example.developerslife.domain.use_case.GetRandomPostUseCase
import com.example.developerslife.presentation.random.RandomViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object DI {

    private lateinit var koinApp: KoinApplication
    private val contentType = "application/json".toMediaType()

    fun init(appContext: Context) {
        koinApp = GlobalContext.startKoin {
//            androidLogger()
            androidContext(appContext)
            modules(
                coreModule(appContext),
                networkingModule(appContext),
                domainModule(appContext),
                presentationModule(appContext)
            )
        }
    }
    private fun coreModule(appContext: Context) = module {
        single {
            Json {
                ignoreUnknownKeys = true
            }
        }
        single {
            Room.databaseBuilder(
                appContext,
                PostDataBase::class.java,
                PostDataBase.DATABASE_NAME
            ).build()
        }

        single {
            get<PostDataBase>().postDao
        }
    }

    private fun domainModule(appContext: Context) = module {
        single<RandomRepository> { RandomRepositoryImpl(get(), get()) }
        single { GetCachedPostsUseCase(get()) }
        single { GetRandomPostUseCase(get()) }
    }


    private fun presentationModule(appContext: Context) = module {
        viewModel { RandomViewModel(get()) }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun networkingModule(appContext: Context) = module {

        single {
            Retrofit.Builder()
                .client(
                    okhttpClient(
                        writeTimeout = 5,
                        callTimeout = 5,
                        connectTimeout = 5,
                        readTimeout = 5,
                        logLevel = HttpLoggingInterceptor.Level.BODY
                    )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(get<Json>().asConverterFactory(contentType))
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(PostApi::class.java)
        }
    }
    private fun okhttpClient(
        writeTimeout: Long = 60,
        callTimeout: Long = 60,
        connectTimeout: Long = 60,
        readTimeout: Long = 60,
        logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.NONE
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .callTimeout(callTimeout, TimeUnit.SECONDS)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)

        if (logLevel != HttpLoggingInterceptor.Level.NONE && BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = logLevel
            })
        }

        return builder.build()
    }
}