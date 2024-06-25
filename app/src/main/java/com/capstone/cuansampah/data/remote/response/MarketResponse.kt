package com.capstone.cuansampah.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class MarketResponse(
    @field:SerializedName("data")
    val data: List<DataMarket> = emptyList(),

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field: SerializedName("error")
    val error: Boolean? = null
)

@Parcelize
data class DataMarket(
    @field: SerializedName("id")
    val id: Int? = null,

    @field: SerializedName("name")
    val name: String? = null,

    @field: SerializedName("price")
    val price: String? = null,

    @field: SerializedName("image")
    val image : String? = null,

    @field: SerializedName("wasteTypeId")
    val wasteTypeId : String? = null,

): Parcelable

//@Parcelize
//data class DataMarket(
//    @field: SerializedName("id")
//    val id: Int? = null,
//
//    @field: SerializedName("wasteName")
//    val wasteName: String? = null,
//
//    @field: SerializedName("wasteType")
//    val wasteType: WasteType? = null,
//
//    @field: SerializedName("amount")
//    val amount : String? = null,
//
//    @field: SerializedName("image")
//    val image : String? = null,
//
//    @field: SerializedName("user")
//    val user : Data? = null,
//): Parcelable

//@Parcelize
//data class WasteType(
//    @field: SerializedName("id")
//    val id: Int? = null,
//
//    @field: SerializedName("name")
//    val wasteName: String? = null,
//
//    @field: SerializedName("price_per_kg")
//    val price : String? = null,
//): Parcelable