package com.capstone.cuansampah.data.remote.retrofit

import com.capstone.cuansampah.data.remote.response.ImageClassificationResponse
import com.capstone.cuansampah.data.remote.response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Headers("Accept: application/json")
    @Multipart
    @POST("predict_image")
    suspend fun uploadImage(
        @Part uploaded_file: MultipartBody.Part
    ): ImageClassificationResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}
