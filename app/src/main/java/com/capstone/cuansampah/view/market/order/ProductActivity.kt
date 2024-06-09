package com.capstone.cuansampah.view.market.order

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.capstone.cuansampah.data.local.Product
import com.capstone.cuansampah.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_PRODUCT, Product::class.java)
        } else {
            intent.getParcelableExtra(KEY_PRODUCT)
        }

        setupView()
        setupAction()
        setData()
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = "Product"
    }

    private fun setupAction() {
        binding.orderButton.setOnClickListener {
            product?.let {
                val intent = Intent(this, OrderActivity::class.java).apply {
                    putExtra(KEY_PRODUCT, it)
                }
                startActivity(intent)
            }
        }

        binding.cartButton.setOnClickListener {
            product?.let {
                val intent = Intent(this, CartActivity::class.java).apply {
                    putExtra(KEY_PRODUCT, it)
                }
                startActivity(intent)
            }
        }
    }

    private fun setData() {
        product?.let {
            with(binding) {
                imageProduct.setImageResource(it.image)
                nameProduct.text = it.name
                priceProduct.text = it.price
                categoryProduct.text = it.category
                stockProduct.text = it.stock
                weightProduct.text = it.weight
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val KEY_PRODUCT = "key_product"
    }
}
