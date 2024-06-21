package com.capstone.cuansampah.data.remote.repository

import androidx.lifecycle.liveData
import com.capstone.cuansampah.data.model.UserModel
import com.capstone.cuansampah.data.remote.UserPreference
import com.capstone.cuansampah.data.remote.response.RegisterResponse
import com.capstone.cuansampah.data.remote.response.ResultResponse
import com.capstone.cuansampah.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class RegisterRepository private constructor(
    private val apiService: ApiService,
    private val preferencesDataSource:
    UserPreference
){
    suspend fun saveSession(user: UserModel) {
        preferencesDataSource.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return preferencesDataSource.getSession()
    }

    suspend fun logout() {
        preferencesDataSource.logout()
    }

    fun userRegister(username: String, email: String, phone_number: String, password: String, confirm_password: String) = liveData {
        emit(ResultResponse.Loading)
        try {
            val successResponse = apiService.register(username, email, phone_number, password, confirm_password)
            emit(ResultResponse.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)

        }
    }
    companion object {
        private const val TAG = "Register View Model"

        @Volatile
        private var instance: RegisterRepository? = null
        fun getInstance(
            preferencesDataSource: UserPreference,
            apiService: ApiService
        ): RegisterRepository =
            instance ?: synchronized(this) {
                instance ?: RegisterRepository(apiService, preferencesDataSource)
            }.also { instance = it }
    }

}