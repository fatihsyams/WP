package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponsePelanggan(
    @SerializedName("customer")
    val customer: List<CustomerApi?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

data class CustomerApi(
    @SerializedName("category_customer_id")
    val categoryCustomerId: Int?,
    @SerializedName("code_customer")
    val codeCustomer: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("customer")
    val customer: String?,
    @SerializedName("discount_customer")
    val discountCustomer: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("no_hp")
    val noHp: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)