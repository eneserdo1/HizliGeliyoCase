package com.eneserdogan.hizligeliyocase.viewmodel

import Product
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eneserdogan.hizligeliyocase.adapter.ProductAdapter
import com.eneserdogan.hizligeliyocase.sevices.ProductAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewmodel : ViewModel() {
    var dataList: List<Product> = ArrayList()
    private val mutableProductList: MutableLiveData<List<Product>> = MutableLiveData()
    val loadingMessage = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Boolean>()

    private val productAPIService = ProductAPIService()

    fun refreshData(): LiveData<List<Product>> {
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
                if (products != null) {
                    dataList = products
                    mutableProductList.postValue(dataList)
                }
                loadingMessage.value = false
                errorMessage.value = false
            }
        })
        return mutableProductList
    }

    /* Gelen string değere göre adapterda ki  original list değerleri filtreleniyor ve filtered liste set ediliyor.
     Çünkü adapterda ki recyclerview değerlerine filtered liste göre değer veriliyor*/
    fun filterList(term: String, adapter: ProductAdapter) {
        if (term != "") {
            println("term  $term")
            val list = adapter.originalList.filter {
                it.category.contains(term, true) || it.title.contains(
                    term,
                    true
                )
            }
            adapter.filterList = list
            adapter.notifyDataSetChanged()
            Log.d("filterList : ", list.toString())

        } else {
            adapter.filterList = adapter.originalList
            adapter.notifyDataSetChanged()
        }

    }

}