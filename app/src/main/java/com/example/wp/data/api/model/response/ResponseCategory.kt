package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponseCategory(
    @SerializedName("category_menu")
    val categoryMenu: List<CategoryMenuApi>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("status_code")
    val statusCode: String? = null
)

data class CategoryMenuApi(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)