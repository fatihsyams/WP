package com.example.wp.di

import com.example.wp.BuildConfig
import com.example.wp.data.preference.SessionManager
import com.example.wp.utils.CustomInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
//    single(named(HEADER_INTERCEPTOR)) { provideHeaderInterceptor(get()) }
    single { provideOkHttpClient(get()) }
    single { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

    return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()
}

fun provideHeaderInterceptor(appPreference: SessionManager): Interceptor {
    val headers = HashMap<String, String>()

    return CustomInterceptor(appPreference)
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY
    return logger
}