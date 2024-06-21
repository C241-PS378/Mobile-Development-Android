package com.capstone.cuansampah.view.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.cuansampah.R
import com.capstone.cuansampah.data.local.Product
import com.capstone.cuansampah.databinding.FragmentMarketBinding
import com.capstone.cuansampah.view.adapter.ProductAdapter

class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding
    private val list = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupRecyclerView()
    }

    private fun setupAction() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    Toast.makeText(context, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }
    }

    private fun getListProducts(): ArrayList<Product> {
        val dataName = resources.getStringArray(R.array.data_product)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val dataImage = resources.obtainTypedArray(R.array.data_photo)
        val dataCategory = resources.getStringArray(R.array.data_category)
        val dataStock = resources.getStringArray(R.array.data_stock)
        val dataWeight = resources.getStringArray(R.array.data_weight)
        val dataDesc = resources.getStringArray(R.array.data_desc)
        val listProduct = ArrayList<Product>()

        for (i in dataName.indices) {
            val product = Product(
                dataName[i],
                dataPrice[i],
                dataImage.getResourceId(i, -1),
                dataCategory[i],
                dataStock[i],
                dataWeight[i],
                dataDesc[i]
            )
            listProduct.add(product)
        }
        return listProduct
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvProduct.setHasFixedSize(true)
            list.addAll(getListProducts())
            rvProduct.layoutManager = GridLayoutManager(context, 2)
            val listProductAdapter = ProductAdapter(list)
            rvProduct.adapter = listProductAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}