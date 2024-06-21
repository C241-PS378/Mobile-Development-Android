package com.capstone.cuansampah.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.cuansampah.data.remote.repository.UserRepository
import com.capstone.cuansampah.data.remote.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, password: String, onSuccess: (LoginResponse) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.login(email, password)
                onSuccess(response)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
