package com.example.wp.base

import android.app.Application
import com.example.wp.di.feature.menuModule
import com.example.wp.di.feature.orderModule
import com.example.wp.di.networkModule
import com.example.wp.di.preferenceModule
import com.example.wp.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WarungPojokApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WarungPojokApplication)
            modules(
                listOf(
                    networkModule,
                    preferenceModule,
                    remoteModule,
                    menuModule,
                    orderModule
                )
            )
        }
    }
}