package com.capstone.cuansampah.data.remote.retrofit

import com.capstone.cuansampah.data.remote.response.ImageClassificationResponse
import com.capstone.cuansampah.data.remote.response.LoginResponse
import com.capstone.cuansampah.data.remote.response.MarketResponse
import com.capstone.cuansampah.data.remote.response.ProdukResponse
import com.capstone.cuansampah.data.remote.response.ProfileResponse
import com.capstone.cuansampah.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") name : String,
        @Field("email") email : String,
        @Field("phone_number") phone_number : String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ) : RegisterResponse

    @GET("profile")
    suspend fun profile(
        @Header("Authorization") token: String,
        @Header("Authorization") cookie: String
    ): ProfileResponse

    @GET("market")
    fun getWaste(
        @Header("Authorization") token: String,
        @Header("Authorization") cookie: String,
    ): Call<MarketResponse>

    @GET("waste/{id}")
    fun getDetailWaste(
        @Header("Authorization") token: String,
        @Header("Authorization") cookie: String,
        @Path("id") id: Int
    ): Call<ProdukResponse>

}
