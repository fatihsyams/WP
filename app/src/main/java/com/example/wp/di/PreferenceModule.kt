package com.example.wp.di

import com.example.wp.data.preference.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    single { SessionManager(androidContext()) }
}