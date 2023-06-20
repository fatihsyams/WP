package com.example.wp.data.api.datasource

import com.example.wp.data.api.service.OrderService
import com.example.wp.data.mapper.OrderMapper
import com.example.wp.domain.payment.Payment
import com.example.wp.domain.repository.PaymentRepository
import com.example.wp.utils.Load

class PaymentRepositoryImpl(private val service: OrderService) : PaymentRepository {
    override suspend fun getPayments(): Load<List<Payment>> {
        return try {
            val response = service.getListPembayaran()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    OrderMapper.mapGetListPembayaran(response)
                } ?: Load.Fail(Throwable(response.message()))
            } else {
                Load.Fail(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Load.Fail(e)
        }
    }
}