package com.example.wp.data.api.model.request

data class RequestMaterialApi(
    val stock: Double,
    val material: String
)

data class RequestQuantityMaterialApi(
    val stock: Double,
    val type: String,
    val reason:String
)

