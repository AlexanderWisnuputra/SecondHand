package com.example.secondhand.wishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.Wishlist
import com.example.secondhand.repository.BuyerRepo

class WishlistVM : ViewModel() {
    private val repo= BuyerRepo()
    private val state = MutableLiveData<bidStatus>()
    private val order = MutableLiveData<List<BidStatus>>()


    private fun loading(b: Boolean) {
        state.value = bidStatus.Loading(b)
    }

   fun getorderedbyid(accestoken: String?,id:Int) {
        loading(true)
        repo.getorderedbyid(accestoken,id) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.value?.get(id) }
        }
    }
    fun getordered(accestoken: String?, status: String) {
        loading(true)

        repo.getordered(accestoken, status) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.postValue(it) }
        }
    }
/*    fun deletewish(accestoken: String?,id: Int) {
        loading(true)

        repo.deletewish(accestoken,id) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.value?.get(id) }
        }
    }*/




    fun getState() =state
    fun getwish() = order
}

sealed class bidStatus{
    data class Loading(val isLoading: Boolean) : bidStatus()
}