package com.example.wp.data

import com.example.wp.data.utils.SessionManager
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WarungPojokService {

    @GET("api/menu")
    suspend fun getDataMenu(): TopResponseMenu

    @GET("api/menu")
    suspend fun getMenu(): ResponseMenuWp

    @GET("api/menu?category_menu_id=0")
    fun getMenuMVP(): Call<ResponseMenuWp>

    @POST("api/login")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLoginn>


    @Multipart
    @POST("api/menu")
    fun createMenu(
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part("stock") stock: RequestBody?,
        @Part("category_menu_id") category_menu_id: RequestBody?,
        @Part image: MultipartBody.Part
    ): Call<ResponseCreateMenu>

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

        class CustomInterceptor(var preferences: SessionManager) : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {

                val newRequest = chain.request().newBuilder()

                newRequest.addHeader("Accept", "application/json")
                if (preferences.getBoolean()) {
                    newRequest.addHeader("Authorization", "Bearer ${preferences.getToken()}")
                }



                return chain.proceed(newRequest.build())


            }
        }
    }
}

