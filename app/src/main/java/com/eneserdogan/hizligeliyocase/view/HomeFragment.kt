package com.eneserdogan.hizligeliyocase.view

import Product
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
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
    private lateinit var productAdapter: ProductAdapter
    var dataList: ArrayList<Product> = ArrayList()
    lateinit var searchView: SearchView
    lateinit var selectedItem:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedItem=requireArguments().getString("selectedItem")!!
        println("oncreate gelen item $selectedItem")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(HomeFragmentViewmodel::class.java)
        searchView = view.findViewById(R.id.searcview)

        //fonksiyonlar
        initializeRecyclerview(dataList)
        filterButtonListener()
        observeData()
        if (selectedItem != "null"){
            viewmodel.filterList(selectedItem,productAdapter)
        }


        //Ana sayfa searchview dinleniyor
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    viewmodel.filterList(p0, productAdapter)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    viewmodel.filterList(p0, productAdapter)
                }
                return false
            }
        })

    }


    private fun filterButtonListener() {
        homeFragmnet_filter_butoon.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_navigation_home_to_filterFragment)

        }
    }

    private fun initializeRecyclerview(dataList: ArrayList<Product>) {
        productAdapter = ProductAdapter()
        product_recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        productAdapter.setAppList(dataList)
        product_recyclerview.adapter = productAdapter
    }

    private fun observeData() {

        /* viewmodel.productList.observe(viewLifecycleOwner, Observer<List<Product>> { products ->
             products?.let {
                 product_recyclerview.visibility = View.VISIBLE
                 productAdapter.productListUpdate(products)
                 itemList=products

             }
         })*/
        viewmodel.refreshData().observe(viewLifecycleOwner, Observer<List<Product>> {
            if (it != null) {
                productAdapter.setAppList(it)
                product_recyclerview.visibility = View.VISIBLE
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