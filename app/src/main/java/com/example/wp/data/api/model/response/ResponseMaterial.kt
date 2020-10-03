package com.example.wp.data.api.model.response


import com.google.gson.annotations.SerializedName

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

data class MaterialApi(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("material")
    val material: String?,
    @SerializedName("stock")
    val stock: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)