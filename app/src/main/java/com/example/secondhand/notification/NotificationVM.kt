package com.example.secondhand.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secondhand.entity.Notification
import com.example.secondhand.repository.NotificationRepo

class NotificationVM(): ViewModel() {
    private val repo= NotificationRepo()
    private val state = MutableLiveData<notificationState>()
    private val notification = MutableLiveData<List<Notification>>()


    private fun loading(b: Boolean) {
        state.value = notificationState.Loading(b)
    }

    fun getnotification(accestoken: String?) {
        loading(true)
        repo.getNotification(accestoken) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { notification.postValue(it) }
        }
    }

    fun getBytype(accestoken: String?,type: String) {
        loading(true)
        repo.getNotificationbyUser(accestoken,type) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { notification.postValue(it) }
        }
    }
    fun getState() =state
    fun getnotification() = notification
}

sealed class notificationState{
    data class Loading(val isLoading: Boolean) : notificationState()
}