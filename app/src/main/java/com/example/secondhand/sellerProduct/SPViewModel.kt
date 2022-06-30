package com.example.secondhand.sellerProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secondhand.entity.SellerProductItem
import com.example.secondhand.sellerProduct.bprepository.ProductRepo

class SPViewModel : ViewModel() {
    private val state = MutableLiveData<MainState>()
    private val products = MutableLiveData<List<SellerProductItem>>()
    private val repo = ProductRepo()
    private fun loading(b: Boolean){
        state.value = MainState.Loading(b)
    }

    fun fetchProducts(){
        loading(true)
        repo.producFunc{
            sproduct, error ->
            loading(false)
            error?.let{it.message?.let { message -> println(message) }}
            sproduct?.let { products.postValue(it) }
        }
    }

    fun getState() =state
    fun getProduct() = products
}

sealed class MainState{
    data class Loading(val isLoading: Boolean) : MainState()
}