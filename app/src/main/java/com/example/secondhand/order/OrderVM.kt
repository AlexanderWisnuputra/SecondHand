package com.example.secondhand.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.Notification
import com.example.secondhand.repository.OrderRepo

class OrderVM(): ViewModel() {
    private val repo= OrderRepo()
    private val state = MutableLiveData<orderState>()
    private val order = MutableLiveData<List<BidStatus>>()


    private fun loading(b: Boolean) {
        state.value = orderState.Loading(b)
    }

    fun getnotification(accestoken: String?) {
        loading(true)
        repo.getOrder(accestoken) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.postValue(it) }
        }
    }
    fun getByID(accestoken: String?,id: Int) {
        loading(true)

        repo.getOrderbyID(accestoken,id) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { order.value?.get(id) }
        }


    }
    fun getState() =state
    fun getnotification() = order
}

sealed class orderState{
    data class Loading(val isLoading: Boolean) : orderState()
}