package com.capstone.cuansampah.view.register

import androidx.lifecycle.ViewModel
import com.capstone.cuansampah.data.remote.repository.UsersRepository

class RegisterViewModel(private val repository: UsersRepository): ViewModel() {
    fun register(username: String, email: String, phone_number: String, password: String, confirm_password: String) = repository.userRegister(username,email,phone_number,password,confirm_password)
}