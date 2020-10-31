package com.example.wp.data.api.model.request

data class RequestMaterialApi(
    val stock: Int,
    val material: String
)

data class RequestQuantityMaterialApi(
    val stock: Int,
    val type: String,
    val reason:String
)

