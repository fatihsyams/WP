package com.example.wp.data

import com.google.gson.annotations.SerializedName


data class TopResponseMenu(
    val success: Boolean,
    val message: String,
    val data: List<ResponseMenu>
)

data class ResponseMenu(
    val id: Int,
    val image: String,
    val name: String,
    val description: String,
    val price: Int,
    val additional_information: String,
    val stock: Int,
    val category: String,
    val created_at: String,
    val updated_at: String
)

data class RequestLogin(
    val username: String,
    val password: String
)

data class ResponseLogin(
    val status: String,
    val status_code: String,
    val message: String,
    val user: List<UserResponse>,
    val token: String
)

data class UserResponse(
    val id: Int,
    val email: String,
    val username: String,
    val created_at: String,
    val updated_at: String
)

data class RequestCreateMenu(
    val name: String,
    val description: String,
    val price: Int,
    val stock: Int,
    val category_menu_id: Int,
    val image: String
)

data class ResponseCreateMenu(

    @field:SerializedName("status_code")
    val statusCode: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("menu")
    val menu: Menu? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class Menu(

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
