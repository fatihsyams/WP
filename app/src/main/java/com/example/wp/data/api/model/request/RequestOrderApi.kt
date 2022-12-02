package com.example.wp.data.api.model.request

import com.google.gson.annotations.SerializedName

data class RequestOrderApi (
    @SerializedName("customer_id")
    val customerId:Int,
    @SerializedName("information")
    val information:String?,
    @SerializedName("order_category")
    val orderCategory:String,
    @SerializedName("table_id")
    val tableId:Int?,
    @SerializedName("menu_price_id")
    val menuIds:String,
    @SerializedName("amount")
    val amounts:String,
    @SerializedName("discount_order")
    val discount:Int?,
    @SerializedName("payment_id")
    val paymentId:Int?,
    @SerializedName("total_payment")
    val totalPayment:Double?,
    @SerializedName("wallet_id")
    val walletId: Int?,
    @SerializedName("total_payment_before_discount")
    val totalPaymentBeforeDiscount: Double?
)

data class RequestUpdateOrderApi(
    @SerializedName("status")
    val status:String
)
