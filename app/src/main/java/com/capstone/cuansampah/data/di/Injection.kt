package com.capstone.cuansampah.data.di

import android.content.Context
import com.capstone.cuansampah.data.remote.UserPreference
import com.capstone.cuansampah.data.remote.dataStore
import com.capstone.cuansampah.data.remote.repository.ImageClassificationRepository
import com.capstone.cuansampah.data.remote.repository.UsersRepository
import com.capstone.cuansampah.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideImageClassificationRepository(context: Context): ImageClassificationRepository{
        val apiService = ApiConfig.getApiService()
        return ImageClassificationRepository.getInstance(apiService)
    }

    fun provideRepository(context:Context): UsersRepository{
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val token = user.token
        val apiService = ApiConfig.getApiServiceUser(token)
        return UsersRepository.getInstance(pref, apiService)
    }

}