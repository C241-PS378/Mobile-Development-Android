package com.capstone.cuansampah.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val price: String,
    val image: Int,
    val category: String,
    val stock: String,
    val weight: String,
    val description: String
): Parcelable
