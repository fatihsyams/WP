package com.example.wp.domain.repository

import com.example.wp.domain.payment.Payment
import com.example.wp.domain.table.Table
import com.example.wp.utils.Load

interface PaymentRepository{
    fun getPayments(): Load<List<Payment>>
}