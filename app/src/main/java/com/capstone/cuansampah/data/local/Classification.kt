package com.capstone.cuansampah.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Classification(
    val name: String,
    val image: Int,
    val bahaya: String,
    val pengolahan: String,
): Parcelable
