package com.example.wp.domain.order

import com.example.wp.utils.emptyString

data class Order(
    val createdAt: String = emptyString(),
    val customerName: String = emptyString(),
    val id: Int = 0,
    val information: String? = null,
    val orderCategory: String = emptyString(),
    val tableId: String? = null,
    val totalPayment: Int = 0,
    val updatedAt: String = emptyString()
)