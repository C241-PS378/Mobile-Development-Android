package com.capstone.cuansampah.data.remote.repository

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.capstone.cuansampah.data.remote.response.ImageClassificationResponse
import com.capstone.cuansampah.data.remote.response.ResultResponse
import com.capstone.cuansampah.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class ImageClassificationRepository private constructor(
    private val apiService: ApiService,
) {
    private val _detailClassification = MutableLiveData<ImageClassificationResponse>()
    val detailClassification: LiveData<ImageClassificationResponse> = _detailClassification
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun uploadImage(imageFile: File) = liveData {
        emit(ResultResponse.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "uploaded_file",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse =
                apiService.uploadImage(multipartBody)

            successResponse.enqueue(object : Callback<ImageClassificationResponse> {
                @SuppressLint("LongLogTag")
                override fun onResponse(
                    call: Call<ImageClassificationResponse>, response: Response<ImageClassificationResponse>
                ) {
                    if (response.isSuccessful) {
                        _detailClassification.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                @SuppressLint("LongLogTag")
                override fun onFailure(call: Call<ImageClassificationResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            emit(ResultResponse.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultResponse.Error("Gambar tidak masuk ke klassifikasi"))
        }
    }
    companion object {
        private const val TAG = "Image Classification Repository"

        @Volatile
        private var instance: ImageClassificationRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ImageClassificationRepository=
            instance ?: synchronized(this) {
                instance ?: ImageClassificationRepository(apiService)
            }.also { instance = it }
    }
}