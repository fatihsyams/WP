package com.example.wp.domain.order

import com.example.wp.utils.emptyString

class Customer (
    val id: Int =0,
    val categoryCustomerId: Int = 0,
    val codeCustomer: String = emptyString(),
    val name: String = emptyString(),
    val discountCustomer: Int = 0,
    val phoneNumber: String = emptyString()
    )
