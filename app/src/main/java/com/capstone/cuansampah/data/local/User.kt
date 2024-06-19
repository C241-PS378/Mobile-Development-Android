package com.capstone.cuansampah.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val phone_number: String,
    val password: String,
    val image: String?,
    val role: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
): Parcelable
