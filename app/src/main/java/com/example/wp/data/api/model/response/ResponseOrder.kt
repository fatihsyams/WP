package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponseOrder(
    @SerializedName("menu")
    val menu: List<MenuApi>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("order")
    val order: OrderApi?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

data class OrderApi(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("customer_name")
    val customerName: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("information")
    val information: String? = null,
    @SerializedName("order_category")
    val orderCategory: String? = null,
    @SerializedName("table_id")
    val tableId: String? = null,
    @SerializedName("total_payment")
    val totalPayment: Int? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)