package com.example.wp.di

import com.example.wp.data.api.service.MenuService
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single { provideMenuApi(get()) }
}

fun provideMenuApi(retrofit: Retrofit): MenuService = retrofit.create(MenuService::class.java)