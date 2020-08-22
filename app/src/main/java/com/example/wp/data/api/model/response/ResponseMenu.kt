package com.example.wp.data.api.model.response

import com.google.gson.annotations.SerializedName

data class ResponseMenuWp(

    @SerializedName("menu")
    val data: List<DataItem>? = null,

    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("message")
    val message: String? = null
)

data class DataItem(

    @SerializedName("menu_images")
    val images: List<MenuImage>? = null,

    @SerializedName("additional_information")
    val additionalInformation: String? = null,

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
    val category: String? = null
)

data class MenuImage(
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
)
