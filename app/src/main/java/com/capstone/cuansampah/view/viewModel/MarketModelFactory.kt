package com.capstone.cuansampah.view.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.cuansampah.data.di.Injection
import com.capstone.cuansampah.data.remote.repository.MarketRepository

class MarketModelFactory private constructor(private val marketRepository: MarketRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MarketViewModel::class.java)) {
            MarketViewModel(marketRepository) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: MarketModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): MarketModelFactory {
            return instance ?: synchronized(MarketModelFactory::class.java) {
                instance?: MarketModelFactory(Injection.provideMarketRepository(context))
                    .also { instance = it }
            }
        }
    }
}
