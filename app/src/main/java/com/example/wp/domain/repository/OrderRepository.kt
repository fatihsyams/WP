package com.example.wp.domain.repository

import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.domain.order.Customer
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.order.Wallet
import com.example.wp.domain.payment.Payment
import com.example.wp.utils.Load

interface OrderRepository{
    suspend fun postOrder(order: OrderResult): Load<OrderResult>
    suspend fun editOrder(order: OrderResult,orderId: Int): Load<Boolean>
    suspend fun updateOrder(orderId:String, status:String): Load<OrderResult>
    suspend fun getOrders(): Load<List<OrderResult>>
    suspend fun getKategoriOrder(): Load<List<KategoriOrder>>
    suspend fun getListPembayaran(): Load<List<Payment>>
    suspend fun getListKas(): Load<List<Wallet>>
    suspend fun getListCustomer(): Load<List<Customer>>
}