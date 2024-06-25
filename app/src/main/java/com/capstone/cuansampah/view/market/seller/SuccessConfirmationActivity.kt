package com.capstone.cuansampah.view.market.seller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.ActivityConfirmationBinding
import com.capstone.cuansampah.databinding.ActivitySuccessConfirmationBinding
import com.capstone.cuansampah.view.main.MainActivity
import com.capstone.cuansampah.view.pickup.PickupFragment

class SuccessConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSuccess.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setupView()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = getString(R.string.success_header)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}