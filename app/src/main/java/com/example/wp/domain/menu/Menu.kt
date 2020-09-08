package com.example.wp.domain.menu

import android.os.Parcelable
import com.example.wp.utils.emptyString
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(
    val images: List<MenuImage> = mutableListOf(),
    var additionalInformation: String = emptyString(),
    val updatedAt: String = emptyString(),
    val price: Int = 0,
    val name: String = emptyString(),
    val description: String = emptyString(),
    val createdAt: String = emptyString(),
    val id: Int = 0,
    val stock: Int = 0,
    val category: String = emptyString(),
    var quantity: Int = 0
) : Parcelable

@Parcelize
data class MenuImage(
    val updatedAt: String = emptyString(),
    val imageUrl: String = emptyString(),
    val id: Int = 0,
    val menuId: Int = 0,
    val createdAt: String = emptyString()
) : Parcelable