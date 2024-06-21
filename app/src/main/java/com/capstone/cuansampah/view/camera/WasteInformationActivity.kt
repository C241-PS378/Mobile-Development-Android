package com.capstone.cuansampah.view.camera

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.cuansampah.R
import com.capstone.cuansampah.data.remote.response.ResultResponse
import com.capstone.cuansampah.databinding.ActivityWasteInformationBinding
import com.capstone.cuansampah.utils.uriToFile
import com.capstone.cuansampah.view.market.MarketFragment

class WasteInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWasteInformationBinding
    private var imageUri: Uri? = null
    private var collector: Boolean? = null
    private var openMap: Boolean? = null
    private var address: String? = null
    private var collector_address: String? = null
    private var lat: Double? = null
    private var lon: Double? = null
    private lateinit var viewModel: ImageClassificationViewModel

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWasteInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ImageClassificationViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ImageClassificationViewModel::class.java]

        imageUri = intent.getParcelableExtra("imageUri")
        address = intent.getStringExtra("address")
        lat = intent.getDoubleExtra("lat", 0.0)
        lon = intent.getDoubleExtra("lon", 0.0)
        collector = intent.getBooleanExtra("collector", false)
        collector_address = intent.getStringExtra("collector_address")
        openMap = intent.getBooleanExtra("openMap", false)

        imageUri?.let { uri ->
            Glide.with(this)
                .load(uri)
                .apply(RequestOptions().transform(RoundedCorners(16))) // 16 is the radius in pixels
                .into(binding.imageWaste)
        }

        binding.sellerAddress.text = address
        binding.collectorAddress.text = collector_address

        if (!address.isNullOrEmpty()) {
            hideMap(true)
        }

        val imageFile = imageUri?.let { uriToFile(it, this) }
        if (imageFile != null) {
            viewModel.uploadImage(imageFile).observe(this) { response ->
                if (response != null) {
                    when (response) {
                        is ResultResponse.Loading -> {
                            showLoading(true)
                            Log.d("Info", "loading")
                        }
                        is ResultResponse.Success -> {
                            showLoading(false)
                            Log.d("Info", "success: ${response.data}")
                            binding.productName.text = response.data.nama.toString()
                            binding.pbContent.text = response.data.bahaya.toString()
                            binding.ppContent.text = response.data.pengolahan.toString()
                            binding.pkContent.text = response.data.jenis_sampah.toString()
                        }
                        is ResultResponse.Error -> {
                            Log.d("InfoError", response.toString())
                        }
                    }
                } else {
                    Log.d("Info", "response is null")
                }
            }
        }

        if (collector == false) {
            binding.btnSellWaste.text = getString(R.string.sell_to_market)
            binding.btnSellWaste.setOnClickListener {
                val intent = Intent(this, MarketFragment::class.java)
                startActivity(intent)
            }

        } else {
            showCollectorAddress(true)
            binding.collectorAddress.text = collector_address
        }

        binding.openMap.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra("imageUri", imageUri)
                collector?.let { putExtra("collector", it) }
            }
            startActivity(intent)
        }

        setupView()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = "Waste Information"
    }

    private fun showCollectorAddress(isShow: Boolean) {
        binding.titleAddress.visibility = if (isShow) View.VISIBLE else View.GONE
        binding.titleCollectorAddress.visibility = if (isShow) View.VISIBLE else View.GONE
        binding.collectorAddress.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun hideMap(isShow: Boolean) {
        binding.openMap.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressedDispatcher
        return true
    }
}
