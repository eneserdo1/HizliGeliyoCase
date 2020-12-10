package com.eneserdogan.hizligeliyocase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.eneserdogan.hizligeliyocase.R
import com.eneserdogan.hizligeliyocase.viewmodel.FilterFragmentViewmodel
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment : Fragment() {
    lateinit var viewmodel: FilterFragmentViewmodel
    private var arrayAdapter:ArrayAdapter<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root=inflater.inflate(R.layout.fragment_filter, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel=ViewModelProviders.of(this).get(FilterFragmentViewmodel::class.java)
        viewmodel.refreshData()




        observeData()
    }

    private fun observeData() {
        viewmodel.categoryListLive.observe(viewLifecycleOwner, Observer {categories->
            categories?.let {
                println("filter fragment $categories")
                arrayAdapter=ArrayAdapter(requireContext(),android.R.layout.simple_list_item_multiple_choice,categories)
                filterFragment_list.adapter=arrayAdapter
            }
        })

        viewmodel.loadingLive.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if (it){
                    filterFragment_loading.visibility=View.VISIBLE
                    filterFragment_list.visibility=View.GONE
                    filterFragment_errorMessage.visibility=View.GONE
                }else{
                    filterFragment_loading.visibility=View.GONE
                    filterFragment_list.visibility=View.VISIBLE
                }
            }
        })

        viewmodel.errorMessage.observe(viewLifecycleOwner, Observer { message->
            message?.let {
                if (it){
                    filterFragment_errorMessage.visibility=View.VISIBLE
                    filterFragment_list.visibility=View.GONE

                }else{
                    filterFragment_errorMessage.visibility=View.GONE
                    filterFragment_list.visibility=View.VISIBLE
                }
            }
        })
    }


}