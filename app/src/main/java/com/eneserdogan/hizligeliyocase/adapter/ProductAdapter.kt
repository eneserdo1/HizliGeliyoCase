package com.eneserdogan.hizligeliyocase.adapter

import Product
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eneserdogan.hizligeliyocase.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class ProductAdapter :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    var filterList = listOf<Product>()
    var originalList = listOf<Product>()
    fun setAppList(modelList: List<Product>) {
        // Gelen list iki farklı değişkene set ediliyor, originalList filtrelenerek filterList değerine aktarılıyor //
        this.filterList = modelList
        this.originalList = modelList
        notifyDataSetChanged()
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Recyclerview itemlarında ki  değerler set ediliyor
        fun bind(data: Product) = with(itemView) {
            recyclerview_textview_price.text = data.price.toString() + " TL"
            recyclerview_textview_title.text = data.title.toString()
            Glide.with(itemView.context).load(data.image).into(recyclerview_image_product) // Ürün resmi glide ile set ediliyor
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return this.filterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Her item için o pozisyonda ki ürüne ait bilgiler bind fonksiyonuna gönderiliyor
        holder.bind(this.filterList[position])

    }
}