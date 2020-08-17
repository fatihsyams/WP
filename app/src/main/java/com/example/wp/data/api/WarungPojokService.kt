package com.example.wp.data.api

import com.example.wp.data.*
import okhttp3.*
import retrofit2.Call
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

}

