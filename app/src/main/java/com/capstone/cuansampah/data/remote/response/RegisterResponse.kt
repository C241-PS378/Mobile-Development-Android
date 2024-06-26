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
