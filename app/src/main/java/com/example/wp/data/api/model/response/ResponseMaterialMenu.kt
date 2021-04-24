package com.example.wp.data.api.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseMaterialMenu(
    @SerializedName("material_menu")
    val materialMenu: MaterialMenuApi?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

data class ResponseMaterialsMenu(
    @SerializedName("material_menu")
    val materialMenu: List<MaterialMenuApi>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

@Parcelize
data class MaterialMenuApi(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("material_id")
    val materialId: Int?,
    @SerializedName("menu_id")
    val menuId: Int?,
    @SerializedName("stock_required")
    val stockRequired: Double?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("menu")
    val menu: MenuApi?,
    @SerializedName("material")
    val material: MaterialApi?
) : Parcelable