package com.capstone.cuansampah.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.cuansampah.R
import com.capstone.cuansampah.data.local.History
import com.capstone.cuansampah.view.adapter.HistoryAdapter

class HistoryFragment : Fragment() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var recyclerView: RecyclerView
    private val historyList = ArrayList<History>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Populate the list with some sample data
        populateHistoryList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.list_history)
        recyclerView.layoutManager = LinearLayoutManager(context)
        historyAdapter = HistoryAdapter(historyList)
        recyclerView.adapter = historyAdapter
        return view
    }

    private fun populateHistoryList() {
        // Add sample data to the list
        historyList.add(History("Sample Name 1", "2023-06-01", "$10.00"))
        historyList.add(History("Sample Name 2", "2023-06-02", "$20.00"))
        historyList.add(History( "Sample Name 3", "2023-06-03", "$30.00"))
    }
}
