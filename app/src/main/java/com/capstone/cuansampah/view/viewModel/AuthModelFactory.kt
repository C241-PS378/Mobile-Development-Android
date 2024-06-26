package com.capstone.cuansampah.view.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.cuansampah.data.di.Injection
import com.capstone.cuansampah.data.remote.repository.UsersRepository

class AuthModelFactory private constructor(private val userRepository: UsersRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(userRepository) as T
        }  else
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

    }

    companion object {
        @Volatile
        private var instance: AuthModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): AuthModelFactory {
            return instance ?: synchronized(AuthModelFactory::class.java) {
                instance?: AuthModelFactory(Injection.provideUserRepository(context))
                    .also { instance = it }
            }
        }
    }
}
