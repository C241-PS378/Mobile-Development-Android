package com.capstone.cuansampah.data.remote.repository

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.capstone.cuansampah.data.remote.response.ImageClassificationResponse
import com.capstone.cuansampah.data.remote.response.ResultResponse
import com.capstone.cuansampah.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        fun getMimeType(file: File): String? {
            val extension = file.extension
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }

        fun createRequestBody(file: File): RequestBody {
            val mimeType = getMimeType(file) ?: "application/octet-stream"
            return file.asRequestBody(mimeType.toMediaTypeOrNull())
        }
        val requestImageFile = createRequestBody(imageFile)
        val multipartBody = MultipartBody.Part.createFormData(
            "uploaded_file", imageFile.name,
            requestImageFile
        )
        Log.d("HasilGambar:", multipartBody.toString())
        try {
            val successResponse =
                apiService.uploadImage(multipartBody)
            emit(ResultResponse.Success(successResponse))
            _detailClassification.value = successResponse
            Log.d("hasil resposn", successResponse.toString())
        } catch (e: HttpException) {
            emit(ResultResponse.Error("Gambar tidak masuk ke klassifikasi : ${e}"))
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