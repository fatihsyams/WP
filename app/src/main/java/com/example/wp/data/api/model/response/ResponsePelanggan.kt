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
    val categoryCustomerId: Int? = null,
    @SerializedName("code_customer")
    val codeCustomer: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("customer")
    val customer: String? = null,
    @SerializedName("discount_customer")
    val discountCustomer: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("no_hp")
    val noHp: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)