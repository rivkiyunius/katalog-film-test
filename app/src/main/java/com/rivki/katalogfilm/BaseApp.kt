package com.rivki.katalogfilm

import android.app.Application
import com.rivki.katalogfilm.di.DepsModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApp)
            modules(DepsModuleProvider.modules)
        }
    }
}