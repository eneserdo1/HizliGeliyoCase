package com.eneserdogan.hizligeliyocase.viewmodel

import Product
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eneserdogan.hizligeliyocase.sevices.ProductAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewmodel : ViewModel() {
    val productList = MutableLiveData<List<Product>>()
    val loadingMessage = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Boolean>()

    private val productAPIService = ProductAPIService()

    fun refreshData() {
        loadingMessage.value = true
        val myCall = productAPIService.getProducts()
        myCall.enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                loadingMessage.value = false
                errorMessage.value = true
                t.printStackTrace()

            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val products = response.body()
                productList.value = products
                loadingMessage.value = false
                errorMessage.value = false
            }
        })
    }
}