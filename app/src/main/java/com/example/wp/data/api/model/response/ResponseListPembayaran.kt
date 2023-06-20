package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponseListPembayaran(
    @SerializedName("message")
    val message: String?,
    @SerializedName("payment")
    val payment: List<PaymentApi?>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

data class PaymentApi(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("payment")
    val payment: String?,
    @SerializedName("updated_at")
    val updatedAt: String?

)