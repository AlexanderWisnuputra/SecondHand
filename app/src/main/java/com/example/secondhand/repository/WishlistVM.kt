package com.example.secondhand.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.Wishlist

class WishlistVM : ViewModel() {
    private val repo= BuyerRepo()
    private val state = MutableLiveData<wishState>()
    private val order = MutableLiveData<List<Wishlist>>()


    private fun loading(b: Boolean) {
        state.value = wishState.Loading(b)
    }

    fun getWish(accestoken: String?) {
        loading(true)
        repo.getWishList(accestoken) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.postValue(it) }
        }
    }
    fun putWish(accestoken: String?,id: Int) {
        loading(true)

        repo.postWish(accestoken,id) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.value?.get(id) }
        }


    }
    fun getState() =state
    fun getnotification() = order
}

sealed class wishState{
    data class Loading(val isLoading: Boolean) : wishState()
}