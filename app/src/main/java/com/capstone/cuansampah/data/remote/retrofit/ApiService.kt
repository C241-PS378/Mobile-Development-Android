package com.capstone.cuansampah.data.remote.retrofit

import com.capstone.cuansampah.data.remote.response.ImageClassificationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict_image")
    suspend fun uploadImage(
        @Part uploaded_file: MultipartBody.Part,
    ) : Call<ImageClassificationResponse>
}