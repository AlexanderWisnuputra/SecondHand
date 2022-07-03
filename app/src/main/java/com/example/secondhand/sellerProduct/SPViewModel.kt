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
    fun fetchProductsbyKursus() {
        loading(true)
        repo.categoryKursus { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { products.postValue(it) }
        }
    }
    fun fetchProductsbySport() {
        loading(true)
        repo.categorySport { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { products.postValue(it) }
        }
    }
    fun fetchProductsbyMakanan() {
        loading(true)
        repo.categoryMakanan() { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { products.postValue(it) }
        }
    }
    fun fetchProductsbyHobi() {
        loading(true)
        repo.categoryhobi() { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { products.postValue(it) }
        }
    }
    fun fetchProductsbySearch(search: String) {
        loading(true)
        repo.searchBar(search) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { products.postValue(it) }
        }
    }
    fun getByID(id: Int) {
        loading(true)
        repo.getByID(id) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { products.value?.get(id) }
        }
    }
    fun getState() =state
    fun getProduct() = products
}

sealed class MainState{
    data class Loading(val isLoading: Boolean) : MainState()
}