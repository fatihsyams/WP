package com.example.wp.data.api.service

import com.example.wp.data.api.model.request.RequestOrderApi
import com.example.wp.data.api.model.response.ResponseOrder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {

    @POST("order")
    suspend fun postOrder(@Body requestOrderApi: RequestOrderApi) : Response<ResponseOrder>

}