package com.example.secondhand.repository

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.Bid
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.History
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepo {
    private val api = ServiceBuilder.instance()

    fun getOrder(accestoken: String?, completion: (List<BidStatus>?, Error?) -> Unit) {
        api.getOrder(accestoken).enqueue(object : Callback<List<BidStatus>> {
            override fun onFailure(call: Call<List<BidStatus>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<List<BidStatus>>,
                response: Response<List<BidStatus>>
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

    fun getOrderbyID(accestoken: String?, id: Int, completion: (BidStatus?, Error?) -> Unit) {
        api.getOrderbyID(accestoken, id).enqueue(object : Callback<BidStatus> {
            override fun onFailure(call: Call<BidStatus>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<BidStatus>,
                response: Response<BidStatus>
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

    fun patchOrder(accestoken: String?, id: Int, completion: (BidStatus?, Error?) -> Unit) {
        api.patchOrder(accestoken, id).enqueue(object : Callback<BidStatus> {
            override fun onFailure(call: Call<BidStatus>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<BidStatus>,
                response: Response<BidStatus>
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

    fun deleteOrder(accestoken: String?, id: Int, completion: (Bid?, Error?) -> Unit) {
        api.deleteOrder(accestoken, id).enqueue(object : Callback<Bid> {
            override fun onFailure(call: Call<Bid>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<Bid>,
                response: Response<Bid>
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
