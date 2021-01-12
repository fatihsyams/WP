package com.example.wp.data.api.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseMaterial(
    @SerializedName("material")
    val material: MaterialApi?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

data class ResponseMaterials(
    @SerializedName("material")
    val material: List<MaterialApi>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

@Parcelize
data class MaterialApi(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("material")
    val material: String? = null,
    @SerializedName("stock")
    val stock: Double? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
) : Parcelable