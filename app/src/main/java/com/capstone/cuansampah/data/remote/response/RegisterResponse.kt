package com.capstone.cuansampah.data.remote.response

import com.google.gson.annotations.SerializedName

class RegisterResponse {
    @field: SerializedName("status")
    val status: String? = null

    @field: SerializedName("message")
    val message: Boolean? = null

    @field: SerializedName("error")
    val error: Boolean? = null

    @field: SerializedName("data")
    val data: Data? = null
}

/**
data class Data(
    @field: SerializedName("id")
    val id: Int? = null,

    @field: SerializedName("username")
    val username: String? = null,

    @field: SerializedName("email")
    val email : String? = null,

    @field: SerializedName("phone_number")
    val phone_number : String? = null,

    @field: SerializedName("password")
    val password : String? = null,

    @field: SerializedName("image")
    val image: Int? = null,

    @field: SerializedName("role")
    val role: String? = null,
)

 **/