package com.capstone.cuansampah.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.cuansampah.R
import com.capstone.cuansampah.data.local.History

class HistoryAdapter(private val listHistory: ArrayList<History>):
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.ic_history)
        val name: TextView = itemView.findViewById(R.id.name)
        val date: TextView = itemView.findViewById(R.id.date)
        val price: TextView = itemView.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = listHistory.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = listHistory[position]
        holder.image.setImageResource(R.drawable.ic_history)
        holder.name.text = historyItem.name
        holder.date.text = historyItem.date
        holder.price.text = historyItem.price
    }
}
