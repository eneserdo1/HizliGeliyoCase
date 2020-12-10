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

class ProductAdapter(val productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    /*fun setProductList(context: Context,productList:List<Product>){
        this.context=context
        if (this.productList==null){
            this.productList=productList
            this.filteredList=productList
            notifyItemChanged(0,filteredList!!.size)
        }else{
            val result=DiffUtil.calculateDiff(object :DiffUtil.Callback(){
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@ProductAdapter.productList!![oldItemPosition].title==productList[newItemPosition].title
                }

                override fun getOldListSize(): Int {
                    return this@ProductAdapter.productList!!.size
                }

                override fun getNewListSize(): Int {
                    return productList.size
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newProduct = this@ProductAdapter.productList!![oldItemPosition]
                    val oldProduct = productList[newItemPosition]
                    return newProduct.title == oldProduct.title
                }
            })
            this.productList=productList
            this.filteredList=productList
            result.dispatchUpdatesTo(this)
        }
    }*/


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data:Product) = with(itemView){
            recyclerview_textview_price.text=data.price.toString() + " TL"
            recyclerview_textview_title.text=data.title.toString()
            Glide.with(itemView.context).load(data.image).into(recyclerview_image_product)
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
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(productList[position])
       /* val recyclerImage: ImageView = holder.itemView.findViewById(R.id.recyclerview_image_product)
        holder.itemView.recyclerview_textview_price.text = productList[position].price.toString() + " TL"
        holder.itemView.recyclerview_textview_title.text = productList[position].title.toString()
        Glide.with(holder.itemView.context).load(productList[position].image).into(recyclerImage)*/
    }

    fun productListUpdate(newList: List<Product>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }

    /*override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString=p0.toString()
                if (charString.isEmpty()){
                    filteredList=productList
                }else{
                    val productFiltered=ArrayList<Product>()
                    for (product in productList!!){
                        if (product.title!!.toLowerCase().contains(charString.toLowerCase())){
                            productFiltered.add(product)
                        }
                    }
                    filteredList=productFiltered
                }
                val filteredResults=FilterResults()
                filteredResults.values=filteredList
                return filteredResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredList=p1?.values as ArrayList<Product>
                notifyDataSetChanged()
            }

        }
    }*/
}