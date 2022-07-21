package com.mbano.eventsapp

import android.app.Application
import android.content.Context
import com.mbano.core.remote.webModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {


    companion object {
        private lateinit var instance: App
        private val splash_Is_Loading = MutableStateFlow(true)
        val splashIsLoading = splash_Is_Loading.asStateFlow()
        // Application context that can be used where appropriate throughout the app
        val appContext: Context by lazy { instance.applicationContext }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin()
        plantTimberTrees()
        splash_Is_Loading.value = false

    }

    private fun startKoin() {
       val modules = listOf(
           EventsModule,webModule
        )
        startKoin {
            androidContext(appContext)
            modules(modules)
        }
    }

    private fun plantTimberTrees() {
        if (BuildConfig.LogApp)
            Timber.plant(DebugTree())
    }

}