package com.capstone.cuansampah.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.cuansampah.data.model.UserModel
import com.capstone.cuansampah.data.remote.repository.UsersRepository
import com.capstone.cuansampah.data.remote.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UsersRepository) : ViewModel() {

    fun login(email: String, password: String)= userRepository.login(email, password)
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
