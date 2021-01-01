package com.example.wp.domain.order

import android.os.Parcelable
import com.example.wp.domain.menu.Menu
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderResult(
    val order: Order = Order(),
    val menu: List<Menu> = listOf(),
    val type:Int = 0,
    val paymentMethod:String = emptyString()
):Parcelable