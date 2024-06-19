package com.capstone.cuansampah.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History (
    val name: String,
    val date: String,
    val price: String
):Parcelable