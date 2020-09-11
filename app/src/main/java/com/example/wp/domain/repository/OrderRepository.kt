package com.example.wp.domain.repository

import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.Load

interface OrderRepository{
    suspend fun postOrder(order: OrderResult): Load<OrderResult>
}