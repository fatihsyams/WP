package com.example.wp.data.api.service

import com.example.wp.data.api.model.request.RequestLogin
import com.example.wp.data.api.model.response.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MenuService {

    @GET("kategori-menu")
    suspend fun getMenuCategories():Response<ResponseCategory>

    @GET("menu")
    suspend fun getMenu(@Query("category_menu_id") categoryId:Int): Response<ResponseMenuWp>

    @POST("login")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLoginn>

    @Multipart
    @POST("menu")
    fun createMenu(
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part("stock") stock: RequestBody?,
        @Part("category_menu_id") category_menu_id: RequestBody?,
        @Part image: MultipartBody.Part
    ): Call<ResponseCreateMenu>

}

