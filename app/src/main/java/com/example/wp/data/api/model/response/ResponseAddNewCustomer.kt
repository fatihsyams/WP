package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponseAddNewCustomer(
    @SerializedName("customer")
    val customer: CustomerApi? = CustomerApi(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("status_code")
    val statusCode: String? = ""
)