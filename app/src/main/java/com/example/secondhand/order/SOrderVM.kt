package com.example.secondhand.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.Notification
import com.example.secondhand.entity.Product
import com.example.secondhand.repository.SOrderRepo
import kotlinx.coroutines.launch
import retrofit2.Response

class SOrderVM(): ViewModel() {
    private val repo= SOrderRepo()
    private val state = MutableLiveData<SorderState>()
    private val order = MutableLiveData<List<Product>>()
    private val order2 = MutableLiveData<Response<Product>>()


    private fun loading(b: Boolean) {
        state.value = SorderState.Loading(b)
    }

    fun getsoldProduct(accestoken: String?) {
        loading(true)
        repo.productList(accestoken) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.postValue(it) }
        }
    }
    fun getsoldProductid(accestoken: String?, id:Int) {
        loading(true)
        viewModelScope.launch {
            loading(false)
            val response = repo.productListbyid(accestoken, id)
            order2.value = response
        }
    }
    fun patch(accestoken: String?, id:Int,sat:String) {
        loading(true)
        repo.patch(accestoken,id,sat) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.value!!.get(id) }
        }
    }

    fun getState() =state
    fun getOrder() = order
    fun getOrder2() = order2

}

sealed class SorderState{
    data class Loading(val isLoading: Boolean) : SorderState()
}