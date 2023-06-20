package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponseListKas(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?,
    @SerializedName("wallet")
    val wallet: List<WalletApi?>?
)

data class WalletApi(
    @SerializedName("created_at")
    val createdAt: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updated_at")
    val updatedAt: Any?,
    @SerializedName("wallet")
    val wallet: String?
)