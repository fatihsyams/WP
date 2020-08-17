package com.example.wp.utils

import com.example.wp.data.api.WarungPojokService
import com.example.wp.data.preference.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {
    companion object {
        private val BASE_URL = "http://warungpojok.snip-id.com/"

        fun create(preferences: SessionManager): WarungPojokService {

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            val httpClientBuilder =
                OkHttpClient.Builder().addInterceptor(CustomInterceptor(preferences))
                    .addInterceptor(logger)
                    .build()


            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WarungPojokService::class.java)
        }
    }
}

class CustomInterceptor(var preferences: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()

        newRequest.addHeader("Accept", "application/json")
        if (preferences.isUserLogin()) {
            newRequest.addHeader("Authorization", "Bearer ${preferences.getToken()}")
        }

        return chain.proceed(newRequest.build())

    }
}