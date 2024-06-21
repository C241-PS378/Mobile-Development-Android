package com.capstone.cuansampah.view.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.cuansampah.data.di.Injection
import com.capstone.cuansampah.data.remote.repository.RegisterRepository

class RegisterViewModelFactory private constructor(private val repository: RegisterRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RegisterViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): RegisterViewModelFactory {
            return INSTANCE ?: synchronized(RegisterViewModelFactory::class.java) {
                INSTANCE ?: RegisterViewModelFactory(Injection.provideRepository(context))
                    .also { INSTANCE = it }
            }
        }
    }
}