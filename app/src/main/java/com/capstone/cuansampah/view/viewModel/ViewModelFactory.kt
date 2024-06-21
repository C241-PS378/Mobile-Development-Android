package com.capstone.cuansampah.view.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.cuansampah.data.di.Injection
import com.capstone.cuansampah.data.remote.repository.UsersRepository
import com.capstone.cuansampah.view.login.LoginViewModel
import com.capstone.cuansampah.view.main.MainActivity
import com.capstone.cuansampah.view.register.RegisterViewModel

class ViewModelFactory private constructor(private val userRepository: UsersRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(userRepository) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(ViewModelFactory::class.java) {
                instance?: ViewModelFactory(Injection.provideRepository(context))
                    .also { instance = it }
            }
        }
    }
}
