package com.example.wp.data.api.service

import com.example.wp.data.api.model.request.RequestLogin
import com.example.wp.data.api.model.response.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MenuService {

    @GET("api/menu?category_menu_id=0")
    suspend fun getMenu(): Response<ResponseMenuWp>

    @GET("api/menu?category_menu_id=0")
    fun getMenuMVP(): Call<ResponseMenuWp>

    @POST("api/login")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLoginn>

    @POST("api/menu/{id}/delete")
    suspend fun deleteMenu(@Path("id") id: Int): Response<ResponseDeleteMenu>

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

