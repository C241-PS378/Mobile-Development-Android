package com.capstone.cuansampah.data.remote.response

import com.google.gson.annotations.SerializedName

class ProdukResponse(
    @field:SerializedName("data")
    val data:DataMarket? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field: SerializedName("error")
    val error: Boolean? = null
)