package com.example.wp.data.api.datasource

import com.example.wp.data.api.service.OrderService
import com.example.wp.data.mapper.OrderMapper
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.repository.OrderRepository
import com.example.wp.utils.Load

class OrderRepositoryImpl (private val service:OrderService):OrderRepository{
    override suspend fun postOrder(order: OrderResult): Load<OrderResult> {
        return try {
            val response = service.postOrder(OrderMapper.mapToRequestOrderApi(order))
            if (response.isSuccessful){
                response.body()?.let {response->
                    OrderMapper.map(response)
                } ?: Load.Fail(Throwable(response.message()))
            }else{
                Load.Fail(Throwable(response.message()))
            }
        }catch (e: Exception){
            Load.Fail(e)
        }
    }
}