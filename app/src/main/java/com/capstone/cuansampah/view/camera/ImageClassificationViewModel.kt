package com.capstone.cuansampah.view.camera

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import com.capstone.cuansampah.data.remote.repository.ImageClassificationRepository
import java.io.File

class ImageClassificationViewModel(private val repository: ImageClassificationRepository)  : ViewModel() {
    val detailClassification = repository.detailClassification
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun uploadImage(uploaded_file: File) = repository.uploadImage(uploaded_file)
}