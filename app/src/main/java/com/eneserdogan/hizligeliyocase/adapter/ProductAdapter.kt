package com.eneserdogan.hizligeliyocase.adapter

import Product
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eneserdogan.hizligeliyocase.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class ProductAdapter(val productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recyclerImage: ImageView = holder.itemView.findViewById(R.id.recyclerview_image_product)
        holder.itemView.recyclerview_textview_price.text =
            productList[position].price.toString() + " TL"
        holder.itemView.recyclerview_textview_title.text = productList[position].title.toString()
        Glide.with(holder.itemView.context).load(productList[position].image).into(recyclerImage)
    }

    fun productListUpdate(newList: List<Product>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }
}