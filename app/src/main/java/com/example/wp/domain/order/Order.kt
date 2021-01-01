package com.example.wp.domain.order

import android.os.Parcelable
import com.example.wp.domain.menu.Menu
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val createdAt: String = emptyString(),
    val customerName: String = emptyString(),
    val id: Int = 0,
    val information: String? = null,
    val orderCategory: String = emptyString(),
    val tableId: String? = null,
    val totalPayment: Double = 0.0,
    val totalPaymentBeforeDiscount:Double = 0.0,
    val updatedAt: String = emptyString(),
    val discount:Int = 0
):Parcelable