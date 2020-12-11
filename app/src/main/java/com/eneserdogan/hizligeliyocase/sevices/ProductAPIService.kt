package com.eneserdogan.hizligeliyocase.sevices

import Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductAPIService {
    private val BASE_URL="https://fakestoreapi.com/"
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductAPI::class.java)

    // API üzerinden çekilen değerler return ediliyor
    fun getProducts():Call<List<Product>>{
        return retrofit.getData()
    }
}