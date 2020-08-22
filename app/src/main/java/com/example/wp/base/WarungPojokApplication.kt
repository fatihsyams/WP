package com.example.wp.base

import android.app.Application
import com.example.wp.di.networkModule
import com.example.wp.di.preferenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WarungPojokApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WarungPojokApplication)
            modules(listOf(networkModule, preferenceModule))
        }
    }
}