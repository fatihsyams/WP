package com.example.wp.di

import com.example.wp.data.api.service.MenuService
import com.example.wp.data.api.service.OrderService
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single { provideMenuApi(get()) }
    single { provideOrderApi(get()) }
}

fun provideMenuApi(retrofit: Retrofit): MenuService = retrofit.create(MenuService::class.java)
fun provideOrderApi(retrofit: Retrofit): OrderService = retrofit.create(OrderService::class.java)