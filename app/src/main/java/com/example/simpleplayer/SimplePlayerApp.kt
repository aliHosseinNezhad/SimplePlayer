package com.example.simpleplayer

import android.app.Application
import com.example.simpleplayer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SimplePlayerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            // Reference Android context
            androidContext(this@SimplePlayerApp)
            // Load modules
            modules(appModule)
        }
    }
}