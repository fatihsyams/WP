package com.example.wp.data.api.service

import com.example.wp.data.api.model.request.RequestOrderApi
import com.example.wp.data.api.model.request.RequestUpdateOrderApi
import com.example.wp.data.api.model.response.ResponseEditOrder
import com.example.wp.data.api.model.response.ResponseKategoriOrder
import com.example.wp.data.api.model.response.ResponseOrders
import com.example.wp.data.api.model.response.ResponsePostOrder
import com.example.wp.domain.kategoriorder.KategoriOrder
import retrofit2.Response
import retrofit2.http.*

interface OrderService {

    @POST("order")
    suspend fun postOrder(@Body requestOrderApi: RequestOrderApi) : Response<ResponsePostOrder>


    @POST("order/{orderId}")
    suspend fun editOrder(
        @Path("orderId") id:Int,
        @Body requestOrderApi: RequestOrderApi) : Response<ResponseEditOrder>


    @POST("order-update-status")
    suspend fun postUpdateOrderStatus(@Body requestUpdateOrderApi: RequestUpdateOrderApi) : Response<ResponsePostOrder>


    @GET("list-bill")
    suspend fun getOrders() : Response<ResponseOrders>

    @GET("kategori-order")
    suspend fun getKategoriOrder() : Response<ResponseKategoriOrder>

}