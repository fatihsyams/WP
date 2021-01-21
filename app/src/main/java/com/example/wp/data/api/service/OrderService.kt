package com.example.wp.data.api.service

import com.example.wp.data.api.model.request.RequestOrderApi
import com.example.wp.data.api.model.request.RequestUpdateOrderApi
import com.example.wp.data.api.model.response.ResponseEditOrder
import com.example.wp.data.api.model.response.ResponseOrders
import com.example.wp.data.api.model.response.ResponsePostOrder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

}