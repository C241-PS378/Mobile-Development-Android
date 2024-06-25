package com.capstone.cuansampah.data.remote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.cuansampah.data.remote.UserPreference
import com.capstone.cuansampah.data.remote.response.DataMarket
import com.capstone.cuansampah.data.remote.response.MarketResponse
import com.capstone.cuansampah.data.remote.response.ProdukResponse
import com.capstone.cuansampah.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarketRepository(
    private val apiService: ApiService,
    private val preferencesDataSource:
    UserPreference
) {
    private val _listWaste = MutableLiveData<List<DataMarket>>()
    val listWaste: LiveData<List<DataMarket>> = _listWaste

    private val _detailWaste = MutableLiveData<DataMarket?>()
    val detailWaste: MutableLiveData<DataMarket?> = _detailWaste

    fun getWaste(token: String, cookie: String) {
        val client = apiService.getWaste(token,cookie)
        client.enqueue(object : Callback<MarketResponse> {
            override fun onResponse(
                call: Call<MarketResponse>, response: Response<MarketResponse>
            ) {
                if (response.isSuccessful) {
                    _listWaste.value = response.body()?.data
                } else {
                    Log.e(TAG, "onFailureRes: ${response}")
                }
            }
            override fun onFailure(call: Call<MarketResponse>, t: Throwable) {
                Log.e(TAG, "onFailureSer: ${t}")
            }
        })
    }

    fun getDetail(token: String, cookie: String, id: Int) {
        val client = apiService.getDetailWaste(token, cookie, id)
        client.enqueue(object : Callback<ProdukResponse> {
            override fun onResponse(
                call: Call<ProdukResponse>, response: Response<ProdukResponse>
            ) {
                if (response.isSuccessful) {
                    _detailWaste.value =  response.body()?.data
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ProdukResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "Market View Model"
        @Volatile
        private var instance: MarketRepository? = null
        fun getInstance(
            preferencesDataSource: UserPreference,
            apiService: ApiService
        ): MarketRepository =
            instance ?: synchronized(this) {
                instance ?: MarketRepository(apiService, preferencesDataSource)
            }.also { instance = it }
    }

}