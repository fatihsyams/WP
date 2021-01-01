package com.example.wp.presentation.listener

import com.example.wp.domain.order.OrderResult

interface OrderResultListener {
    fun onBillClicked(orderResult: OrderResult)
    fun onPayClicked(orderResult: OrderResult)
    fun onCancelClicked(orderResult: OrderResult)
    fun onOrderClicked(orderResult: OrderResult)
}