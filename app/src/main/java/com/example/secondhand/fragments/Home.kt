package com.example.secondhand.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondhand.Helper
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentHomeBinding
import com.example.secondhand.entity.SellerProductItem
import com.example.secondhand.sellerProduct.*


class Home : Fragment(), ProductInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var vmod: SPViewModel
    private var products = MutableLiveData<List<SellerProductItem>>()
    private lateinit var sharedPref: Helper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = homeBinding
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Helper(requireContext())
        setupRecyclerView()
        setupViewModel()
        observe()
        getdata()
        doubleBackToExit()
        binding.category1.setBackgroundResource(R.drawable.selected_category_border)
        binding.category1.setTextColor(Color.WHITE)
    }

    private fun getdata() = vmod.fetchProducts()
    private fun getdataCategory1() = vmod.fetchProductsbyKursus()
    private fun getdataCategory2() = vmod.fetchProductsbySport()
    private fun getdataCategory3() = vmod.fetchProductsbyMakanan()
    private fun getdataCategory4() = vmod.fetchProductsbyHobi()
    private fun getdataSearch(search: String) = vmod.fetchProductsbySearch(search)
    private fun getbyID(id: Int) = vmod.getByID(id)

    private fun setupViewModel(){
        vmod = ViewModelProvider(this).get(SPViewModel::class.java)
    }

    private fun observe(){
        observeState()
        observeProduct()
    }

    private fun observeState() = vmod.getState().observe(viewLifecycleOwner, Observer { handlestate(it)})
    private fun observeProduct() = vmod.getProduct().observe(viewLifecycleOwner, Observer { handleproduct(it)})

    private fun handlestate(it: MainState){
        when(it){
            is MainState.Loading -> isLoading(it.isLoading)
        }
    }

    private fun isLoading(b: Boolean){
        if(b){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }

    private fun handleproduct( sp: List<SellerProductItem>){
        binding.sellerProductRecyclerview.adapter?.let { a->
            if(a is Adapters){
                a.updateList(sp)
            }
        }
        binding.category1.setOnClickListener {
            binding.sellerProductRecyclerview.adapter?.let { a->
                if(a is Adapters){
                    getdata()
                    a.updateList(sp)
                    a.notifyDataSetChanged()
                }
            }
            binding.category1.setBackgroundResource(R.drawable.selected_category_border)
            binding.category1.setTextColor(Color.WHITE)
            binding.category1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_clicked,0,0,0)
            binding.category2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category3.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category4.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category5.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category2.setBackgroundResource(R.drawable.category_border)
            binding.category2.setTextColor(Color.BLACK)
            binding.category3.setBackgroundResource(R.drawable.category_border)
            binding.category3.setTextColor(Color.BLACK)
            binding.category4.setBackgroundResource(R.drawable.category_border)
            binding.category4.setTextColor(Color.BLACK)
            binding.category5.setBackgroundResource(R.drawable.category_border)
            binding.category5.setTextColor(Color.BLACK)
        }

        binding.category2.setOnClickListener {
            binding.sellerProductRecyclerview.adapter?.let { a->
                if(a is Adapters){
                    getdataCategory1()
                    a.updateList(sp)
                    a.notifyDataSetChanged()
                }
            }
            binding.category1.setBackgroundResource(R.drawable.category_border)
            binding.category1.setTextColor(Color.BLACK)
            binding.category2.setBackgroundResource(R.drawable.selected_category_border)
            binding.category2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_clicked,0,0,0)
            binding.category1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category3.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category4.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category5.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category2.setTextColor(Color.WHITE)
            binding.category3.setBackgroundResource(R.drawable.category_border)
            binding.category3.setTextColor(Color.BLACK)
            binding.category4.setBackgroundResource(R.drawable.category_border)
            binding.category4.setTextColor(Color.BLACK)
            binding.category5.setBackgroundResource(R.drawable.category_border)
            binding.category5.setTextColor(Color.BLACK)
        }
        binding.category3.setOnClickListener {
            binding.sellerProductRecyclerview.adapter?.let { a->
                if(a is Adapters){
                    getdataCategory2()
                    a.updateList(sp)
                    a.notifyDataSetChanged()
                }
            }
            binding.category1.setBackgroundResource(R.drawable.category_border)
            binding.category1.setTextColor(Color.BLACK)
            binding.category2.setBackgroundResource(R.drawable.category_border)
            binding.category2.setTextColor(Color.BLACK)
            binding.category3.setBackgroundResource(R.drawable.selected_category_border)
            binding.category3.setTextColor(Color.WHITE)
            binding.category3.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_clicked,0,0,0)
            binding.category2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category4.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category5.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category4.setBackgroundResource(R.drawable.category_border)
            binding.category4.setTextColor(Color.BLACK)
            binding.category5.setBackgroundResource(R.drawable.category_border)
            binding.category5.setTextColor(Color.BLACK)
        }
        binding.category4.setOnClickListener {
            binding.sellerProductRecyclerview.adapter?.let { a->
                if(a is Adapters){
                    getdataCategory3()
                    a.updateList(sp)
                    a.notifyDataSetChanged()
                }
            }
            binding.category1.setBackgroundResource(R.drawable.category_border)
            binding.category1.setTextColor(Color.BLACK)
            binding.category2.setBackgroundResource(R.drawable.category_border)
            binding.category2.setTextColor(Color.BLACK)
            binding.category3.setBackgroundResource(R.drawable.category_border)
            binding.category3.setTextColor(Color.BLACK)
            binding.category4.setBackgroundResource(R.drawable.selected_category_border)
            binding.category4.setTextColor(Color.WHITE)
            binding.category1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category3.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category5.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category4.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_clicked,0,0,0)
            binding.category5.setBackgroundResource(R.drawable.category_border)
            binding.category5.setTextColor(Color.BLACK)
        }
        binding.category5.setOnClickListener {
            binding.sellerProductRecyclerview.adapter?.let { a->
                if(a is Adapters){
                    getdataCategory4()
                    a.updateList(sp)
                    a.notifyDataSetChanged()
                }
            }
            binding.category1.setBackgroundResource(R.drawable.category_border)
            binding.category1.setTextColor(Color.BLACK)
            binding.category2.setBackgroundResource(R.drawable.category_border)
            binding.category2.setTextColor(Color.BLACK)
            binding.category3.setBackgroundResource(R.drawable.category_border)
            binding.category3.setTextColor(Color.BLACK)
            binding.category4.setBackgroundResource(R.drawable.category_border)
            binding.category4.setTextColor(Color.BLACK)
            binding.category5.setBackgroundResource(R.drawable.selected_category_border)
            binding.category5.setTextColor(Color.WHITE)
            binding.category5.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_clicked,0,0,0)
            binding.category1.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category3.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)
            binding.category4.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_unclicked,0,0,0)

        }
        binding.imgPoster.setOnClickListener {
            val search = binding.editText.text.toString()
            binding.sellerProductRecyclerview.adapter?.let { a->
                if(a is Adapters){
                    getdataSearch(search)
                    a.updateList(sp)
                    a.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setupRecyclerView() {
            binding.sellerProductRecyclerview.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )
                adapter = Adapters(mutableListOf(), this@Home)
            }
        }

    override fun click(item: SellerProductItem) {
        var x = item.id
        getbyID(x)
        val mBundle = Bundle()
                mBundle.putString("name_product", item.name)
                mBundle.putString("category_product", item.categories[0].name)
                mBundle.putString("poster", item.imageUrl)
                mBundle.putString("description_product", item.description)
                mBundle.putString("price_product", "Price ${item.basePrice}")
        findNavController().navigate(R.id.action_home_to_buyer_Product_Add, mBundle)
    }

    private fun doubleBackToExit() {
        var doubleBackPressed: Long = 0
        val toast = Toast.makeText(requireContext(), "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT)
        requireActivity().onBackPressedDispatcher.addCallback(this@Home) {
            if (doubleBackPressed + 2000 > System.currentTimeMillis()) {
                activity?.finish()
                toast.cancel()
            } else {
                toast.show()
            }
            doubleBackPressed = System.currentTimeMillis()
        }
    }
}