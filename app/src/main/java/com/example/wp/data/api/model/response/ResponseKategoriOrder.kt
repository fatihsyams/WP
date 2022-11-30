package com.example.wp.data.api.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseKategoriOrder(
    @SerializedName("categoryOrder")
    val categoryOrder: List<CategoryOrderApi?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("status_code")
    val statusCode: String?
)

@Parcelize
data class CategoryOrderApi(
    @SerializedName("category_order")
    val categoryOrder: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
): Parcelable