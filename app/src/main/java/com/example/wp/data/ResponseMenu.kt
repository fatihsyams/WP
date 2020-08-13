package com.example.wp.data


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
    val username : String,
    val password: String
)

data class ResponseLogin(
    val status : String,
    val status_code : String,
    val message : String,
    val user : List<UserResponse>,
    val token : String
)

data class UserResponse(
    val id : Int,
    val email : String,
    val username : String,
    val created_at: String,
    val updated_at: String
)