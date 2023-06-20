package com.example.wp.domain.kategoriorder

import android.os.Parcelable
import com.example.wp.utils.emptyString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KategoriOrder(
    val name: String = emptyString(),
    val id: Int = 0
): Parcelable
