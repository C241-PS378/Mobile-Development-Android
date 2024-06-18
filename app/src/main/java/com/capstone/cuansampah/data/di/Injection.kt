package com.capstone.cuansampah.data.di

import android.content.Context
import com.capstone.cuansampah.data.remote.repository.ImageClassificationRepository
import com.capstone.cuansampah.data.remote.retrofit.ApiConfig
import com.capstone.cuansampah.view.camera.CameraFragment
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideImageClassificationRepository(context: Context): ImageClassificationRepository{
        val apiService = ApiConfig.getApiService()
        return ImageClassificationRepository.getInstance(apiService)
    }
}