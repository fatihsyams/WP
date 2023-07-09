package com.example.wp.data.api.model.request

import com.google.gson.annotations.SerializedName

data class RequestCustomerApi(
    @SerializedName("customer")
    val customer:String,
    @SerializedName("category_customer_id")
    val categoryCustomerId:Int,
    @SerializedName("code_customer")
    val customerCode:String,
    @SerializedName("discount_customer")
    val discount:Int,
    @SerializedName("no_hp")
    val phoneNumber:String
)
