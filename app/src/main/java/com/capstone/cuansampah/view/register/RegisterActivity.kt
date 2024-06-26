package com.capstone.cuansampah.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.capstone.cuansampah.data.remote.response.ResultResponse
import com.capstone.cuansampah.databinding.ActivityRegisterBinding
import com.capstone.cuansampah.view.login.LoginActivity
import com.capstone.cuansampah.view.viewModel.AuthViewModel
import com.capstone.cuansampah.view.viewModel.AuthModelFactory

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<AuthViewModel> {
        AuthModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = ""
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            val username = binding.edUsername.text.toString()
            val email = binding.edEmail.text.toString()
            val phone_number = binding.edPhone.text.toString()
            val password = binding.edPassword.text.toString()
            val confirm_password = binding.edPassword.text.toString()
            viewModel.register(username, email, phone_number, password, confirm_password)
                .observe(this) { response ->
                    Log.d("RegisterResponse", response.toString())
                    if (response != null) {
                        when (response) {
                            is ResultResponse.Loading -> {
                                showLoading(true)
                            }
                            is ResultResponse.Success -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Selamat")
                                    setMessage("Anda berhasil register.")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        val intent =
                                            Intent(this@RegisterActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    create()
                                    show()
                                    showLoading(false)
                                }
                            }
                            is ResultResponse.Error -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Error")
                                    setNegativeButton("Oke") { it, _ ->
                                        it.dismiss()
                                        it.cancel()
                                    }
                                    create()
                                    show()
                                }
                                showLoading(false)
                            }
                        }
                    }
                }
            binding.tvLogin.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}