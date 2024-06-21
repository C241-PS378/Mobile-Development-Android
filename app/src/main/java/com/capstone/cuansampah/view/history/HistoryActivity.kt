package com.capstone.cuansampah.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.cuansampah.data.local.History
import com.capstone.cuansampah.databinding.ActivityHistoryBinding
import com.capstone.cuansampah.view.adapter.HistoryAdapter

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var recyclerView: RecyclerView
    private val historyList = ArrayList<History>()
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setRecycleView()
        populateHistoryList()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = "History"
    }

    private fun setRecycleView() {
        recyclerView = binding.listHistory
        recyclerView.layoutManager = LinearLayoutManager(this)
        historyAdapter = HistoryAdapter(historyList)
        recyclerView.adapter = historyAdapter
    }

    private fun populateHistoryList() {
        // Add sample data to the list
        historyList.add(History("Sample Name 1", "2023-06-01", "$10.00"))
        historyList.add(History("Sample Name 2", "2023-06-02", "$20.00"))
        historyList.add(History( "Sample Name 3", "2023-06-03", "$30.00"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}