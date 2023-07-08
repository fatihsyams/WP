package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponseCustomerCategories(
    @SerializedName("category_customer")
    val categoryCustomer: List<CategoryCustomerApi>? = listOf(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("status_code")
    val statusCode: String? = ""
)

data class CategoryCustomerApi(
    @SerializedName("category_code")
    val categoryCode: String? = "",
    @SerializedName("category_customer")
    val categoryCustomer: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("updated_at")
    val updatedAt: String? = ""
)