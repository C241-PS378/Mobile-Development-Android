package com.capstone.cuansampah.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.cuansampah.data.model.UserModel
import com.capstone.cuansampah.data.remote.response.ResultResponse
import com.capstone.cuansampah.databinding.ActivityLoginBinding
import com.capstone.cuansampah.view.main.MainActivity
import com.capstone.cuansampah.view.register.RegisterActivity
import com.capstone.cuansampah.view.viewModel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = ""
    }
    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            viewModel.login(email, password).observe(this) { response ->
                Log.d("LoginResult", response.toString())
                if (response != null) {
                    when (response) {
                        is ResultResponse.Loading -> {
                            showLoading(true)
                        }

                        is ResultResponse.Success -> {
                            Log.d("SuccessLogin", response.toString() )
                            response.data.token?.let { it1 ->
                                UserModel(
                                    email,
                                    it1
                                )
                            }?.let { it2 ->
                                viewModel.saveSession(
                                    it2
                                )
                            }
                            AlertDialog.Builder(this).apply {
                                setTitle("Selamat")
                                setMessage("Anda berhasil login.")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
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
                                setMessage(response.error)
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

            binding.forgotPassword.setOnClickListener {
                startActivity(Intent(this, ResetPasswordActivity::class.java))
            }

            binding.tvRegister.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}