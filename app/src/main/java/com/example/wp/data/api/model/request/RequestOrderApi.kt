package com.example.wp.data.api.model.request

import com.google.gson.annotations.SerializedName

data class RequestOrderApi (
    @SerializedName("customer_name")
    val customerName:String,
    @SerializedName("information")
    val information:String?,
    @SerializedName("order_category")
    val orderCategory:String,
    @SerializedName("table_id")
    val tableId:String?,
    @SerializedName("menu_id")
    val menuIds:String,
    @SerializedName("amount")
    val amounts:String
)
