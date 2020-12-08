package com.example.wp.data.api.datasource

import com.example.wp.domain.payment.Payment
import com.example.wp.domain.repository.PaymentRepository
import com.example.wp.utils.Load

class PaymentRepositoryImpl() : PaymentRepository {
    override fun getPayments(): Load<List<Payment>> {
        val datas = listOf(
            Payment("Cash"),
            Payment("Credit Card"),
            Payment("Debet / Transfer"),
            Payment("Ovo"),
            Payment("Gopay")
        )
        return Load.Success(datas)
    }
}