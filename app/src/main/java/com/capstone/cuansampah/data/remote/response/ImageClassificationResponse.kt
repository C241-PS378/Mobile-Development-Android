package com.capstone.cuansampah.data.remote.response

import com.google.gson.annotations.SerializedName

data class ImageClassificationResponse(
    @field: SerializedName("nama")
    val nama: String? = null,
    @field: SerializedName("Jenis Sampah")
    val jenis_sampah: String? = null,
    @field: SerializedName("Bahaya")
    val bahaya: String? = null,
    @field: SerializedName("Pengolahan")
    val pengolahan: String? = null,
)
