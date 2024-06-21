package com.capstone.cuansampah.data.remote.repository

import com.capstone.cuansampah.data.remote.retrofit.ApiService
import com.capstone.cuansampah.data.remote.response.LoginResponse

class UserRepository private constructor(
    private val apiService: ApiService,
) {

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}
