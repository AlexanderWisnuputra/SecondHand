package com.example.secondhand.repository

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.BidStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyerRepo {
    private val api = ServiceBuilder.instance()


    fun getordered(accestoken: String?, status:String, completion: (List<BidStatus>?, Error?) -> Unit) {
        api.getordered(accestoken, status).enqueue(object : Callback<List<BidStatus>> {
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


    fun getorderedbyid(accestoken: String?,id:Int, completion: (BidStatus?, Error?) -> Unit) {
        api.getorderedbyid(accestoken,id).enqueue(object : Callback<BidStatus> {
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

/*    fun deletewish(accestoken: String?,id:Int, completion: (BidStatus?, Error?) -> Unit) {
        api.deletewish(accestoken,id).enqueue(object : Callback<BidStatus> {
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
    }*/
}