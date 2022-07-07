package com.example.secondhand.repository

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.History
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryRepo {
    private val api = ServiceBuilder.instance()

    fun getHistory(accestoken: String?, completion: (List<History>?, Error?) -> Unit) {
        api.getHistory(accestoken).enqueue(object : Callback<List<History>> {
            override fun onFailure(call: Call<List<History>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<List<History>>,
                response: Response<List<History>>
            ) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }
        })
    }
    fun getHistorybyID(accestoken: String?,id:Int, completion: (History?, Error?) -> Unit) {
        api.getHistorybyID(accestoken,id).enqueue(object : Callback<History> {
            override fun onFailure(call: Call<History>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<History>,
                response: Response<History>
            ) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }
        })
    }

}
