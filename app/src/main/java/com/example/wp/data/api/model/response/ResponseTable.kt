package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponseTable(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("status_code")
    val statusCode: String? = null,
    @SerializedName("table")
    val table: List<TableApi>? = null
)

data class TableApi(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("number")
    val number: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)