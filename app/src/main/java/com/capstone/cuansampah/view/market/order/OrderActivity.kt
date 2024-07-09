package com.capstone.cuansampah.view.market.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.cuansampah.R
import com.capstone.cuansampah.data.local.Product
import com.capstone.cuansampah.databinding.ActivityOrderBinding
class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.getParcelableExtra(ProductActivity.KEY_PRODUCT)

        setupView()
        setupAction()
        setData()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = getString(R.string.order_header)
    }

    private fun setupAction() {
        binding.cardAddress.setOnClickListener {  }
        binding.cardProduct.setOnClickListener {  }
        binding.cardPayment.setOnClickListener {  }
        binding.orderButton.setOnClickListener {  }
    }
    private fun setData() {
        product?.let {
            with(binding) {
                productImage.setImageResource(it.image)
                productName.text = it.name
                tvAmount.text = it.price
//                deliveryService.text = it.deliveryService
//                deliveryFee.text = "Rp. ${it.deliveryFee},-"
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
