package com.example.developerslife.domain.use_case

import com.example.developerslife.common.Resource
import com.example.developerslife.data.data_source.PostDao
import com.example.developerslife.domain.model.Post
import com.example.developerslife.domain.repository.DevLifePostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetPostUseCase(
    private val repository: DevLifePostRepository,
    private val postDao: PostDao
) {
    operator fun invoke(): Flow<Resource<Post>> = flow {
        try {
            emit(Resource.Loading())
            val post = repository.getPost()
            postDao.insert(post)

            emit(Resource.Success(post))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}