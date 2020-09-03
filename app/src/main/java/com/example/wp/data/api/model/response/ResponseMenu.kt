package com.example.wp.data.api.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseMenuWp(

    @SerializedName("menu")
    val data: List<MenuApi>? = null,

    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("message")
    val message: String? = null
)

data class ResponseDeleteMenu(
    val status: String?,
    val status_code: String?,
    val message: String?
)

@Parcelize
data class MenuApi(

    @SerializedName("menu_images")
    val images: List<MenuImageApi>? = null,

    @SerializedName("additional_information")
    var additionalInformation: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("stock")
    val stock: Int? = null,

    @SerializedName("category")
    val category: String? = null,

//    @SerializedName("category")
    var quantity: Int? = null
) : Parcelable

@Parcelize
data class MenuImageApi(
    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("image")
    val imageUrl: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("menu_id")
    val menuId: Int? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
) : Parcelable
