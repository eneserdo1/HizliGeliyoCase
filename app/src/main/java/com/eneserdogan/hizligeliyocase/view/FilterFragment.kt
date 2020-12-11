package com.eneserdogan.hizligeliyocase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.eneserdogan.hizligeliyocase.R
import com.eneserdogan.hizligeliyocase.viewmodel.FilterFragmentViewmodel
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment : Fragment() {
    lateinit var viewmodel: FilterFragmentViewmodel
    private var arrayAdapter:ArrayAdapter<String>?=null
    private var selectedItem: String=""

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

        closeButtonListener()
        observeData()
        listviewListener()
        filterButtonListener()
    }

    private fun filterButtonListener() {
       filterFragment_button.setOnClickListener {
           val homeFragment=HomeFragment()
           val bundle=Bundle()
           bundle.putString("selectedItem",selectedItem)
           homeFragment.arguments=bundle

           Navigation.findNavController(requireView()).navigate(R.id.action_filterFragment_to_navigation_home,bundle)
       }
    }

    private fun listviewListener() {
        filterFragment_list.onItemClickListener=AdapterView.OnItemClickListener{adapterView, view, i, l ->
            val item=adapterView.getItemAtPosition(i).toString()
            this.selectedItem=item
            println("selected item $item")
        }
    }

    private fun closeButtonListener() {
        filterFragment_close_button.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_filterFragment_to_navigation_home)
        }
    }

    private fun observeData() {
        viewmodel.categoryListLive.observe(viewLifecycleOwner, Observer {categories->
            categories?.let {
                println("filter fragment $categories")
                arrayAdapter=ArrayAdapter(requireContext(),android.R.layout.simple_list_item_single_choice,categories)
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