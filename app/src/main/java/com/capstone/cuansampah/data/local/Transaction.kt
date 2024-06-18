package com.capstone.cuansampah.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id : Int,
    val username : String,
    val email : String,
    val phone_number : String,
    val role : String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val name_product: String,
    val price: String,
    val image_product: Int,
    val category: String,
    val weight: String,
): Parcelable
