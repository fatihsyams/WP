package com.example.wp.domain.table

import android.os.Parcelable
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Table(
    val id: Int = 0,
    val number: String = emptyString()
): Parcelable