package com.example.wp.di

import com.example.wp.data.api.service.MaterialService
import com.example.wp.data.api.service.MenuService
import com.example.wp.data.api.service.OrderService
import com.example.wp.data.api.service.TableService
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single { provideMenuApi(get()) }
    single { provideOrderApi(get()) }
    single { provideTableApi(get()) }
    single { provideMaterialApi(get()) }
}

fun provideMenuApi(retrofit: Retrofit): MenuService = retrofit.create(MenuService::class.java)
fun provideOrderApi(retrofit: Retrofit): OrderService = retrofit.create(OrderService::class.java)
fun provideTableApi(retrofit: Retrofit): TableService = retrofit.create(TableService::class.java)
fun provideMaterialApi(retrofit: Retrofit): MaterialService = retrofit.create(MaterialService::class.java)