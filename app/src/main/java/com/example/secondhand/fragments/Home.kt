package com.example.secondhand.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secondhand.R
import com.example.secondhand.databinding.FragmentHomeBinding
import com.example.secondhand.entity.SellerProductItem
import com.example.secondhand.sellerProduct.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class Home : Fragment(), ProductInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var vmod: SPViewModel
    private var products = MutableLiveData<List<SellerProductItem>>()

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
        setupRecyclerView()
        setupViewModel()
        observe()
        getdata()
        doubleBackToExit()
    }

    private fun getdata() = vmod.fetchProducts()

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
    }

    private fun setupRecyclerView() {
            binding.sellerProductRecyclerview.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false )
                adapter = Adapters(mutableListOf(), this@Home)
            }
        }

    override fun click(item: SellerProductItem) {
    Toast.makeText(requireContext(), item.name,Toast.LENGTH_SHORT).show()
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