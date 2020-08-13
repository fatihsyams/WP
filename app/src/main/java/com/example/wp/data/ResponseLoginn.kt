package com.example.wp.data

import com.google.gson.annotations.SerializedName

data class ResponseLoginn(

	@field:SerializedName("status_code")
	val statusCode: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class User(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: Any? = null,

	@field:SerializedName("username")
	val username: String? = null
)
