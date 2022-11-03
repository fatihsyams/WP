package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponseKategoriOrder(
    @SerializedName("categoryOrder")
    val categoryOrder: List<CategoryOrderApi?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

data class CategoryOrderApi(
    @SerializedName("category_order")
    val categoryOrder: String?,
    @SerializedName("created_at")
    val createdAt: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updated_at")
    val updatedAt: Any?
)