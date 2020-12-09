package com.eneserdogan.hizligeliyocase.view

import Product
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.eneserdogan.hizligeliyocase.R
import com.eneserdogan.hizligeliyocase.adapter.ProductAdapter
import com.eneserdogan.hizligeliyocase.viewmodel.HomeFragmentViewmodel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var viewmodel: HomeFragmentViewmodel
    private var productAdapter = ProductAdapter(arrayListOf())


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

        lifecycleScope.launch {
            viewmodel.refreshData()
        }
        observeData()
    }

    private fun initializeRecyclerview() {
        product_recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        product_recyclerview.adapter = productAdapter
    }

    private fun observeData() {
        viewmodel.productList.observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                product_recyclerview.visibility = View.VISIBLE
                productAdapter.productListUpdate(products)

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