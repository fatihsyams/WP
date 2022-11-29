package com.example.wp.domain.payment

import android.os.Parcelable
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payment(
    val id: Int = 0,
    val name: String = emptyString()
) : Parcelable