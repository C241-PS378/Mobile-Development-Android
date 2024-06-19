package com.capstone.cuansampah.data.remote.repository

import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.capstone.cuansampah.data.remote.response.ImageClassificationResponse
import com.capstone.cuansampah.data.remote.response.ResultResponse
import com.capstone.cuansampah.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class ImageClassificationRepository private constructor(
    private val apiService: ApiService,
) {
    private val _detailClassification = MutableLiveData<ImageClassificationResponse>()
    val detailClassification: LiveData<ImageClassificationResponse> = _detailClassification

    fun uploadImage(imageFile: File) = liveData {
        emit(ResultResponse.Loading)

        fun getMimeType(file: File): String? {
            val extension = file.extension
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }

        fun createRequestBody(file: File): MultipartBody.Part {
            val mimeType = getMimeType(file) ?: "application/octet-stream"
            val requestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())
            return MultipartBody.Part.createFormData("uploaded_file", file.name, requestBody)
        }

        val multipartBody = createRequestBody(imageFile)
        Log.d("HasilGambar:", multipartBody.toString())

        try {
            val successResponse = apiService.uploadImage(multipartBody)
            emit(ResultResponse.Success(successResponse))
            _detailClassification.value = successResponse
            emit(ResultResponse.Error("HTTPSuccess: ${successResponse}}"))
        } catch (e: HttpException) {
            emit(ResultResponse.Error("HTTPError: ${e.message()}"))
        } catch (e: Exception) {
            emit(ResultResponse.Error("Unexpected error: ${e.message}"))
        }
    }

    companion object {
        private const val TAG = "Image Classification Repository"

        @Volatile
        private var instance: ImageClassificationRepository? = null
        fun getInstance(apiService: ApiService): ImageClassificationRepository =
            instance ?: synchronized(this) {
                instance ?: ImageClassificationRepository(apiService)
            }.also { instance = it }
    }
}
