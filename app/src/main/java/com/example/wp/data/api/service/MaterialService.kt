package com.example.wp.data.api.service

import com.example.wp.data.api.model.request.RequestMaterialApi
import com.example.wp.data.api.model.request.RequestMaterialMenuApi
import com.example.wp.data.api.model.request.RequestOrderApi
import com.example.wp.data.api.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface MaterialService {

    @POST("bahan")
    suspend fun postMaterial(@Body requestMaterialApi: RequestMaterialApi) : Response<ResponseMaterial>

    @GET("bahan")
    suspend fun getMaterials() : Response<ResponseMaterials>

    @POST("bahan/{id}/edit")
    suspend fun getMaterial(@Path("id") materialId:Int) : Response<ResponseMaterial>

    @POST("bahan/{id}")
    suspend fun editMaterial(@Path("id") materialId:Int, @Body requestMaterialApi: RequestMaterialApi) : Response<ResponseMaterial>

    @POST("bahan-menu")
    suspend fun postMaterialMenu(@Body requestMaterialMenuApi: RequestMaterialMenuApi) : Response<ResponseMaterialMenu>

    @GET("bahan-menu")
    suspend fun getMaterialMenu(@Query("menu_id") menuId:Int) : Response<ResponseMaterialsMenu>

    @POST("bahan-menu/{id}")
    @FormUrlEncoded
    suspend fun editMaterialMenu(@Path("menu_id") menuId:Int,@FieldMap hashMap: HashMap<String,Int>) : Response<ResponseMaterialMenu>
}