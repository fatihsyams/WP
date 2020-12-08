package com.example.wp.data.api.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseMenuWp(

    @SerializedName("menu")
    val data: EndlessMenuApi? = null,

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

data class EndlessMenuApi(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("data")
    val menu : List<MenuApi>?,
    @SerializedName("first_page_url")
    val firstPageUrl: String?,
    @SerializedName("from")
    val from: Int?,
    @SerializedName("last_page")
    val lastPage: Int?,
    @SerializedName("last_page_url")
    val lastPageUrl: String?,
    @SerializedName("next_page_url")
    val nextPageUrl: String?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("prev_page_url")
    val prevPageUrl: String?,
    @SerializedName("to")
    val to: Int?,
    @SerializedName("total")
    val total: Int?
)

@Parcelize
data class MenuApi(

    @SerializedName("image")
    val images: String? = null,

    @SerializedName("additional_information")
    var additionalInformation: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("price")
    val price: Double? = null,

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

    var quantity: Int? = null,

    @SerializedName("price_gofood")
    val goFoodPrice: Double? = null,

    @SerializedName("price_grabfood")
    val grabFoodPrice: Double? = null,

    @SerializedName("material_menus")
    val materialMenus: List<MaterialMenuApi>? = null,

    @SerializedName("discount")
    val discount:Int? = null,

    @SerializedName("discount_takeaway")
    val discountTakeAway:Int? = null

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
