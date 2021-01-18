package com.example.wp.data.api.model.request

import com.google.gson.annotations.SerializedName

data class RequestOrderApi (
    @SerializedName("customer_name")
    val customerName:String,
    @SerializedName("information")
    val information:String?,
    @SerializedName("order_category")
    val orderCategory:String,
    @SerializedName("table_id")
    val tableId:String?,
    @SerializedName("menu_id")
    val menuIds:String,
    @SerializedName("amount")
    val amounts:String,
    @SerializedName("discount_order")
    val discount:Int?,
    @SerializedName("pembayaran")
    val paymentMethod:String?,
    @SerializedName("total_payment")
    val totalPayment:Double?,
    @SerializedName("total_payment_before_discount")
    val totalPaymentBeforeDiscount:Double?
)

data class RequestUpdateOrderApi(
    @SerializedName("id")
    val orderId:String,
    @SerializedName("status")
    val status:String
)
