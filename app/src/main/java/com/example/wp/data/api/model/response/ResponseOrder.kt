package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

data class ResponsePostOrder(
    @SerializedName("message")
    val message: String?,
    @SerializedName("order")
    val order: OrderApi?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

data class ResponseEditOrder(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

data class OrderApi(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("customer")
    val customer: CustomerApi?= null,
    @SerializedName("customer_id")
    val customerId: Int?= null,
    @SerializedName("information")
    val information: String? = null,
    @SerializedName("discount_order")
    val discountOrder: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("order_date_id")
    val orderDateId: Int? = null,
    @SerializedName("order_status")
    val orderStatus: String? = null,
    @SerializedName("payment_id")
    val paymentId: String? = null,
    @SerializedName("table_id")
    val tableId: String? = null,
    @SerializedName("total_payment")
    val totalPayment: Double? = null,
    @SerializedName("total_payment_before_discount")
    val totalPaymentBeforeDiscount: Double? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("wallet_id")
    val walletId: String? = null,
    val orderMenuApi : List<OrderMenuApi>? = null,
    val paymentMethod: String? = null,
    val categoryOrder: String? = null
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
    @SerializedName("information")
    var additionalInformation: String? = null,
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