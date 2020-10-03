package com.example.wp.domain.material

data class MaterialMenu(
    val id: Int = 0,
    val materialId: Int,
    val menuId: Int,
    val stockRequired: Int
)