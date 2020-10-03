package com.example.wp.data.api.model.request

data class RequestMaterialMenuApi(
    val materialId: Int,
    val menuId: Int,
    val stockRequired: Int
)