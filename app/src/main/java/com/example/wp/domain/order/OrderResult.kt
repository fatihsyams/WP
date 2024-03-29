package com.example.wp.domain.order

import android.os.Parcelable
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.payment.Payment
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderResult(
    val order: Order = Order(),
    var menu: List<Menu> = listOf(),
    val type:String = emptyString(),
    val paymentMethod:Payment = Payment(),
    val status:String = ""
):Parcelable