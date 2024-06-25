package com.capstone.cuansampah.view.viewModel

import androidx.lifecycle.ViewModel
import com.capstone.cuansampah.data.remote.repository.MarketRepository

class MarketViewModel(private val repository: MarketRepository) : ViewModel(){
    val listWaste = repository.listWaste
    val detailWaste = repository.detailWaste
    fun getWastes(token: String, cookie: String) = repository.getWaste(token, cookie)

    fun getWaste(token: String, cookie: String, id:Int) = repository.getDetail(token, cookie, id)
}