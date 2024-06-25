package com.capstone.cuansampah.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)
