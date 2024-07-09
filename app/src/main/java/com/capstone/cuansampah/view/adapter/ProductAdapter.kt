package com.capstone.cuansampah.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.cuansampah.R
import com.capstone.cuansampah.data.local.Product
import com.capstone.cuansampah.view.market.order.ProductActivity


class ProductAdapter(private val listProduct: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_product)
        val tvName: TextView = itemView.findViewById(R.id.tv_product_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val (name, price, image) = listProduct[position]
        holder.imgPhoto.setImageResource(image)
        holder.tvName.text = name
        holder.tvPrice.text = price

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductActivity::class.java)
            intent.putExtra("key_product", listProduct[holder.adapterPosition])
            holder.itemView.context.startActivity(intent)
        }
    }

}
