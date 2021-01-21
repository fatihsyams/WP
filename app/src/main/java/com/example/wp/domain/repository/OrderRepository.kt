package com.example.wp.domain.repository

import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.Load

interface OrderRepository{
    suspend fun postOrder(order: OrderResult): Load<OrderResult>
    suspend fun editOrder(order: OrderResult,orderId: Int): Load<Boolean>
    suspend fun updateOrder(orderId:String, status:String): Load<Boolean>
    suspend fun getOrders(): Load<List<OrderResult>>
}