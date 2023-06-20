package com.example.wp.domain.order

import android.os.Parcelable
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wallet
    (
    val id: Int = 0,
    val name: String = emptyString()
) : Parcelable