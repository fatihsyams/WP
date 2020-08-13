package com.example.wp.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WarungPojokService {

    @GET("api/menu")
    suspend fun getDataMenu(): TopResponseMenu

    @GET("api/menu")
    suspend fun getMenu(): ResponseMenuWp

    @GET("api/menu")
    fun getMenuMVP(): Call<ResponseMenuWp>

    @POST("api/login")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLoginn>

    companion object {
        private val BASE_URL = "http://warungpojok.snip-id.com/"

        fun create(): WarungPojokService {

            val httpClientBuilder =
                OkHttpClient.Builder().addInterceptor(CustomInterceptor()).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WarungPojokService::class.java)
        }

        class CustomInterceptor() : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {

                val newRequest = chain.request().newBuilder()

                newRequest.addHeader("Accept", "application/json")


                return chain.proceed(newRequest.build())


            }
        }
    }


}