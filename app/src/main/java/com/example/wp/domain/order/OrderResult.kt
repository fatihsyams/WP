package com.example.wp.domain.order

import com.example.wp.domain.menu.Menu
import com.example.wp.utils.emptyString

data class OrderResult(
    val order: Order = Order(),
    val menu: List<Menu> = listOf(),
    val type:Int = 0
)