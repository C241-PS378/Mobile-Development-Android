package com.capstone.cuansampah.view.camera

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.cuansampah.data.di.Injection
import com.capstone.cuansampah.data.remote.repository.ImageClassificationRepository

class ImageClassificationViewModelFactory private constructor(private val repository: ImageClassificationRepository) :
    ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        return if(modelClass.isAssignableFrom(ImageClassificationViewModel::class.java)){
            return ImageClassificationViewModel(repository) as T
        }
        else{
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        private var instance: ImageClassificationViewModelFactory ? = null
        fun getInstance(context: Context):  ImageClassificationViewModelFactory  {
            return instance ?: synchronized(this) {
                instance ?:  ImageClassificationViewModelFactory (Injection.provideImageClassificationRepository(context))
                    .also { instance = it }
            }
        }
    }
}