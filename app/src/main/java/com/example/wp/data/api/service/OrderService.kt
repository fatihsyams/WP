package com.example.wp.data.api.service

import com.example.wp.data.api.model.request.RequestCustomerApi
import com.example.wp.data.api.model.request.RequestOrderApi
import com.example.wp.data.api.model.request.RequestUpdateOrderApi
import com.example.wp.data.api.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface OrderService {

    @POST("order")
    suspend fun postOrder(@Body requestOrderApi: RequestOrderApi) : Response<ResponsePostOrder>

    @POST("order/{orderId}")
    suspend fun editOrder(
        @Path("orderId") id:Int,
        @Body requestOrderApi: RequestOrderApi) : Response<ResponseEditOrder>

    @POST("order-update-status/{orderId}")
    suspend fun postUpdateOrderStatus(
        @Path("orderId") id:Int,
        @Body requestUpdateOrderApi: RequestUpdateOrderApi) : Response<ResponseUpdateStatusOrder>

    @GET("list-bill")
    suspend fun getOrders() : Response<ResponseOrders>

    @GET("kategori-order")
    suspend fun getKategoriOrder() : Response<ResponseKategoriOrder>

    @GET("pembayaran")
    suspend fun getListPembayaran() : Response<ResponseListPembayaran>

    @GET("kas")
    suspend fun getListKas() : Response<ResponseListKas>

    @GET("pelanggan")
    suspend fun getPelanggan() : Response<ResponsePelanggan>

    @POST("pelanggan")
    suspend fun postNewCustomer(request:RequestCustomerApi) : Response<ResponseAddNewCustomer>

    @GET("kategori-pelanggan")
    suspend fun getCustomerCategories() : Response<ResponseCustomerCategories>





}