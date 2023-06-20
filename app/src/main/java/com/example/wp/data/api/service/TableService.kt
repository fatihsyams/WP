package com.example.wp.data.api.service

import com.example.wp.data.api.model.response.ResponseTable
import retrofit2.Response
import retrofit2.http.GET

interface TableService {

    @GET("table")
    suspend fun getTables(): Response<ResponseTable>

}