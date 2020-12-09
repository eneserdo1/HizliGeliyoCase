package com.eneserdogan.hizligeliyocase.sevices

import Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {

    @GET("products")
    fun getData(): Call<List<Product>>
}