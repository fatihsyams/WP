package com.example.wp.data.api.service

import com.example.wp.data.api.model.request.RequestLoginApi
import com.example.wp.data.api.model.response.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MenuService {

    @GET("menu?category_menu_id=0")
    suspend fun getMenu(): Response<ResponseMenuWp>

    @GET("menu?category_menu_id=0")
    fun getMenuMVP(): Call<ResponseMenuWp>

    @GET("kategori-menu")
    suspend fun getMenuCategories():Response<ResponseCategory>

    @GET("menu")
    suspend fun getMenu(@Query("category_menu_id") categoryId:Int): Response<ResponseMenuWp>

    @POST("login")
    fun login(@Body requestLoginApi: RequestLoginApi): Call<ResponseLoginn>

    @POST("menu/{id}/delete")
    suspend fun deleteMenu(@Path("id") id: Int): Response<ResponseDeleteMenu>

    @FormUrlEncoded
    @POST("menu/{id}/edit-stock")
    fun updateStock(
        @Path("id") id: Int,
        @FieldMap stok: HashMap<String, Int>
    ): Call<ResponseUpdateStock>


    @Multipart
    @POST("api/menu/{id}/edit-menu")
    fun updateMenu(
        @Path("id") id: Int,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part("stock") stock: RequestBody?,
        @Part("category_menu_id") category_menu_id: RequestBody?,
        @Part image: MultipartBody.Part
    ): Call<ResponseUpdateData>

    @Multipart
    @POST("menu")
    fun createMenu(
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part("stock") stock: RequestBody?,
        @Part("category_menu_id") category_menu_id: RequestBody?,
        @Part("price_grabfood") grabFoodPrice: RequestBody?,
        @Part("price_gofood") goFoodPrice: RequestBody?,
        @Part image: MultipartBody.Part
    ): Call<ResponseCreateMenu>


}

