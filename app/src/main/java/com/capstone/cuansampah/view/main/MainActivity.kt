package com.capstone.cuansampah.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.cuansampah.databinding.ActivityMainBinding
import com.capstone.cuansampah.view.onboarding.OnboardingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, OnboardingActivity::class.java)

        binding.btnWelcome.setOnClickListener {
            startActivity(intent)
        }

    }
}