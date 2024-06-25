package com.capstone.cuansampah.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.cuansampah.data.remote.response.DataMarket
import com.capstone.cuansampah.databinding.ItemProductBinding
import com.capstone.cuansampah.view.market.order.ProductActivity

class MarketAdapter : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    private val wastes: MutableList<DataMarket> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(view)

    }

    override fun getItemCount(): Int {
        return wastes.size
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val story = wastes[position]
        holder.bind(story)
    }

    inner class MarketViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataMarket) {
            binding.tvProductName.text = item.name.toString()
            binding.apply {
                Glide.with(binding.root)
                    .load("https://backend-2g7newb35q-et.a.run.app/public/images/profile/${item.image}")
                    .centerCrop()
                    .into(binding.ivProduct)
            }
            binding.tvProductPrice.text ="Rp. ${item.price}"
            binding.cardProduct.setOnClickListener() {
                val intent = Intent(it.context, ProductActivity::class.java)
                intent.putExtra(ProductActivity.KEY_PRODUCT, item.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMarket(newMarket: List<DataMarket>) {
        Log.e(TAG, "SetStory Adapter")
        wastes.clear()
        wastes.addAll(newMarket)
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "MarketView"
        private const val ID = "product_key"
    }

}
