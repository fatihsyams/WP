package com.example.wp.domain.material

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MaterialMenu(
    val id: Int = 0,
    val materialId: Int,
    val menuId: Int,
    val stockRequired: Double,
    val material: Material = Material()
) : Parcelable