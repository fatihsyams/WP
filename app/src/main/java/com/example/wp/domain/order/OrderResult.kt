package com.example.wp.domain.order

import com.example.wp.domain.menu.Menu

data class OrderResult(
    val order: Order = Order(),
    val menu: List<Menu> = listOf()
)