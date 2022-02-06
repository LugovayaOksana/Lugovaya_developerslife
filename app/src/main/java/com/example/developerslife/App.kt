package com.example.developerslife

import android.app.Application
import com.example.developerslife.data.di.DI
import timber.log.Timber

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        setupLogger()
        DI.init(this)
    }

    private fun setupLogger() {
        Timber.plant(Timber.DebugTree())
    }
}