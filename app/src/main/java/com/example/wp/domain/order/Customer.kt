package com.example.wp.domain.order

import com.example.wp.utils.emptyString

class Customer (
    val id: Int =0 ,
    val  codeCustomer: Int = 0,
    val naem: String = emptyString(),
    val discountCustomer: Int = 0
    )
