package com.eneserdogan.hizligeliyocase.view

import Product
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.eneserdogan.hizligeliyocase.R
import com.eneserdogan.hizligeliyocase.adapter.ProductAdapter
import com.eneserdogan.hizligeliyocase.viewmodel.HomeFragmentViewmodel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class HomeFragment : Fragment() {
    private lateinit var viewmodel: HomeFragmentViewmodel
    private var productAdapter = ProductAdapter(arrayListOf())
    private var itemList:List<Product>?=null
    private var filteredList:List<Product>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(HomeFragmentViewmodel::class.java)

        initializeRecyclerview()
        /*lifecycleScope.launch {

        }*/

        filterButtonListener()

        viewmodel.refreshData()
        println("runblocking içerisi")
        observeData()

        println("itemlist $itemList")



    }



    private fun filterButtonListener() {
        homeFragmnet_filter_butoon.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_navigation_home_to_filterFragment)

        }
    }

    private fun initializeRecyclerview() {
        product_recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        product_recyclerview.adapter = productAdapter
    }

    private fun observeData() {

        viewmodel.productList.observe(viewLifecycleOwner, Observer<List<Product>> { products ->
            products?.let {
                product_recyclerview.visibility = View.VISIBLE
                productAdapter.productListUpdate(products)
                itemList=products

            }
        })

        viewmodel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                if (it) {
                    hataMesajı.visibility = View.VISIBLE
                    product_recyclerview.visibility = View.GONE
                } else {
                    hataMesajı.visibility = View.GONE
                }
            }
        })

        viewmodel.loadingMessage.observe(viewLifecycleOwner, Observer { message ->
            message?.let {
                if (it) {
                    ürünYükleniyor.visibility = View.VISIBLE
                    product_recyclerview.visibility = View.GONE
                    hataMesajı.visibility = View.GONE
                } else {
                    ürünYükleniyor.visibility = View.GONE
                }
            }
        })
    }


}