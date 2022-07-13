package com.example.secondhand.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secondhand.entity.History
import com.example.secondhand.repository.HistoryRepo


class HistoryVM() : ViewModel() {
    private val repo= HistoryRepo()
    private val state = MutableLiveData<HistoryState>()
    private val history = MutableLiveData<List<History>>()


    private fun loading(b: Boolean) {
        state.value = HistoryState.Loading(b)
    }

    fun getHistory(accestoken: String?) {
        loading(true)
        repo.getHistory(accestoken) { sproduct, error ->
            loading(false)
            error?.let { it.message?.let { message -> println(message) } }
            sproduct?.let { history.postValue(it) }
        }
    }
        fun getByID(accestoken: String?,id: Int) {
            loading(true)

            repo. getHistorybyID(accestoken,id) { sproduct, error ->
                loading(false)
                error?.let { it.message?.let { message -> println(message) } }
                sproduct?.let { history.value?.get(id) }
            }


    }
    fun getState() =state
    fun getHistory() = history
}

sealed class HistoryState{
    data class Loading(val isLoading: Boolean) : HistoryState()
}