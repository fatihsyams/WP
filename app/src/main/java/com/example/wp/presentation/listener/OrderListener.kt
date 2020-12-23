package com.example.wp.presentation.listener

import com.example.wp.domain.order.OrderResult

interface OrderResultListener {
    fun onBillClicked(order: OrderResult)
    fun onPayClicked(order: OrderResult)
    fun onCancelClicked(order: OrderResult)
    fun onOrderClicked(order: OrderResult)
}