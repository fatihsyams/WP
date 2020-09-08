package com.example.wp.utils

import com.example.wp.data.api.service.MenuService
import com.example.wp.data.preference.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkUtils {
    companion object {
        private val BASE_URL = "http://warungpojok.snip-id.com/"

        fun create(preferences: SessionManager): MenuService {

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            val httpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(CustomInterceptor(preferences))
                .addInterceptor(logger)
                .build()


            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MenuService::class.java)
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

fun <T: Any> handleApiSuccess(data: T) : Load.Success<T>{
    return Load.Success(data)
}

fun <T : Any> handleApiError(response: retrofit2.Response<T>): Load.Fail<T> {
    return Load.Fail(Throwable(response.message()))
}
