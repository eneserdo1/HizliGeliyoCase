package com.eneserdogan.hizligeliyocase.viewmodel

import Product
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eneserdogan.hizligeliyocase.sevices.ProductAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterFragmentViewmodel:ViewModel() {
    val categoryListLive=MutableLiveData<List<String>>()
    val listOfCategory: ArrayList<String> = ArrayList<String>()
    val loadingLive=MutableLiveData<Boolean>()
    val errorMessage=MutableLiveData<Boolean>()
    private val productAPIService = ProductAPIService()

    fun refreshData() {
        loadingLive.value=true
        val myCall = productAPIService.getProducts()
        myCall.enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                errorMessage.value=true
                loadingLive.value=false
                t.printStackTrace()
            }
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val products = response.body()!!
                for (item in products){
                    if (listOfCategory.contains(item.category)){

                    }else{
                        listOfCategory.add(item.category)
                    }
                }
                errorMessage.value=false
                loadingLive.value=false
                categoryListLive.value=listOfCategory
            }
        })
    }
}