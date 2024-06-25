package com.capstone.cuansampah.view.market.seller

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.ActivityConfirmationBinding
import com.capstone.cuansampah.view.camera.MapsActivity

class ConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmationBinding
    private var imageUri: Uri? = null
    private var collector: Boolean? = null
    private var seller_address: String? = null
    private var collector_address: String? = null
    private var lat: Double? = null
    private var lon: Double? = null
    private var waste_weight: Double? = null
    private var waste_price: Int? = null
    private var waste_name: String? = null
    private var total_income: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageUri = intent.getParcelableExtra("imageUri")
        collector_address = intent.getStringExtra("collector_address")
        seller_address = intent.getStringExtra("seller_address")
        lat = intent.getDoubleExtra("lat", 0.0)
        lon = intent.getDoubleExtra("lon", 0.0)
        waste_weight = intent.getDoubleExtra("waste_weight",0.0)
        waste_price = intent.getIntExtra("waste_price",0)
        waste_name= intent.getStringExtra("waste_name")
        collector = intent.getBooleanExtra("collector", false)
        setupView()
        imageUri?.let { uri ->
            Glide.with(this)
                .load(uri)
                .apply(RequestOptions().transform(RoundedCorners(16))) // 16 is the radius in pixels
                .into(binding.imageWaste)
        }
        binding.cvName.text = waste_name
        binding.cvType.text = "Plastik"
        binding.cvWeight.text = "${waste_weight.toString()} kg"
        total_income = waste_price!! * waste_weight!!
        binding.cvIncome.text = "Rp. ${total_income}"
        binding.cvAddress.text = seller_address
        binding.cvAddress.text = seller_address
        binding.cvContactAddress.text = "085702436630"
        binding.cvNameCollector.text = "Ahmad Zidan"
        binding.cvContactCollector.text = "082134572890"
        binding.cvCollector.text = collector_address
        binding.btnSellConfirmation.setOnClickListener {
            val intent = Intent(this, SuccessConfirmationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = getString(R.string.confirmation_header)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val KEY_PRODUCT = "key_product"
    }
}