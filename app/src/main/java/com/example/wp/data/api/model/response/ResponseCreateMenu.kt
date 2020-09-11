package com.example.wp.data.api.model.response

import com.google.gson.annotations.SerializedName

data class ResponseCreateMenu(

    @field:SerializedName("status_code")
    val statusCode: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("menu")
    val menu: CreateMenuApi? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class CreateMenuApi(

    @field:SerializedName("additional_information")
    val additionalInformation: Any? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("price")
    val price: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("category_menu_id")
    val categoryMenuId: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("stock")
    val stock: String? = null
)
