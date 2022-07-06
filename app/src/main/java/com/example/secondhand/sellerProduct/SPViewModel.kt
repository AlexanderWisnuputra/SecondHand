package com.example.secondhand.sellerProduct

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secondhand.Helper
import com.example.secondhand.entity.SellerProductItem
import com.example.secondhand.sellerProduct.bprepository.ProductRepo
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class SPViewModel : ViewModel() {
    private val state = MutableLiveData<MainState>()
    private val products = MutableLiveData<List<SellerProductItem>>()
    private val repo = ProductRepo()
    private lateinit var sharedPref: Helper
    val showResponseSuccess: MutableLiveData<String> = MutableLiveData()

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

    fun PostProduct(
        acstkn: String?,
        name:String,
        description: String,
        basePrice: Int,
        categoryIds: List<Int>,
        location: String,
        productImage: File
    ){

        viewModelScope.launch{
           val at = acstkn
            val requestFile = productImage.asRequestBody("image/png".toMediaTypeOrNull())
            val requestImage = MultipartBody.Part.createFormData("image", productImage.name,requestFile)
            val name = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val desc = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val basePrice = basePrice.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val location = location.toRequestBody("text/plain".toMediaTypeOrNull())
            val category = categoryIds[0].toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val postProduct = repo.addProuct(at,name,desc,basePrice,category, location,requestImage)
            ServiceBuilder.instance().addProduct(
                at,name,desc,basePrice,category, location,requestImage
            )
            if(postProduct.isSuccessful){
                showResponseSuccess.postValue("Yeehaw")
                postProduct.body()

            }            }
    }

    fun getState() =state
    fun getProduct() = products
}


sealed class MainState{
    data class Loading(val isLoading: Boolean) : MainState()
}