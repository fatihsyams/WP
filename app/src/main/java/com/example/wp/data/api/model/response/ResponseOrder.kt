package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponsePostOrder(
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
    val totalPayment: Double? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("discount_order")
    val discountOrder: String?= null,
    @SerializedName("order_date_id")
    val orderDateId: Int? = 0,
    @SerializedName("order_menus")
    val orderMenuApis: List<OrderMenuApi>? = listOf(),
    @SerializedName("order_status")
    val orderStatus: String? = null,
    @SerializedName("pembayaran")
    val pembayaran: String? = null
)

data class ResponseOrders(
    @SerializedName("dataorder")
    val dataorder: List<OrderApi>? = listOf(),
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("status_code")
    val statusCode: String? = null
)

data class OrderMenuApi(
    @SerializedName("amount")
    val amount: Int? = 0,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("menu")
    val menu: MenuApi? = null,
    @SerializedName("menu_id")
    val menuId: Int? = 0,
    @SerializedName("order_id")
    val orderId: Int? = 0,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)