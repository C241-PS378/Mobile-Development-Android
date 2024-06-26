package com.capstone.cuansampah.data.remote.response

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("address")
	val address: Any? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
