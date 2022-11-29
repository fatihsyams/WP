package com.example.wp.domain.order

import android.os.Parcelable
import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.table.Table
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val createdAt: String = emptyString(),
    val customerId: Int = 0,
    val customerName:String = emptyString(),
    val information: String = emptyString(),
    val orderCategory: KategoriOrder = KategoriOrder(),
    val id:Int = 0,
    val table: Table = Table(),
    val totalPayment: Double = 0.0,
    val totalPaymentBeforeDiscount:Double = 0.0,
    val updatedAt: String = emptyString(),
    val discount:Int = 0,
    val wallet: Wallet = Wallet()
):Parcelable