package com.example.wp.di

import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
//    single { provideMembershipApi(get()) }
}

//fun provideMembershipApi(retrofit: Retrofit): Membership = retrofit.create(Membership::class.java)