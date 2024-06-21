package com.capstone.cuansampah.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.cuansampah.databinding.ActivityLoginBinding
import com.capstone.cuansampah.view.main.MainActivity
import com.capstone.cuansampah.view.register.RegisterActivity
import com.capstone.cuansampah.view.viewModel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        setupViewModel()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = ""
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(application)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            binding.progressBar.visibility = View.VISIBLE

            loginViewModel.login(email, password, { response ->

                binding.progressBar.visibility = View.GONE
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, { error ->

                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Login failed: ${error.message}", Toast.LENGTH_SHORT).show()
            })
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

