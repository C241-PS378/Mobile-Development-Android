package com.capstone.cuansampah.view.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.cuansampah.data.model.UserModel
import com.capstone.cuansampah.data.remote.repository.UsersRepository
import com.capstone.cuansampah.data.remote.response.LoginResponse
import com.capstone.cuansampah.data.remote.response.ResultResponse
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UsersRepository): ViewModel() {
    fun register(username: String, email: String, phone_number: String, password: String, confirm_password: String) = userRepository.userRegister(username,email,phone_number,password,confirm_password)
    fun login(email: String, password: String)= userRepository.login(email, password)

    fun profile(token: String)= userRepository.profile(token)
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
    fun saveSession(user: UserModel){
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

}

