package com.example.secondhand.repository

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.History
import com.example.secondhand.entity.Wishlist
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyerRepo {
    private val api = ServiceBuilder.instance()


    fun postWish(accestoken: String?,id:Int, completion: (Wishlist?, Error?) -> Unit) {
        api.postwish(accestoken,id).enqueue(object : Callback<Wishlist> {
            override fun onFailure(call: Call<Wishlist>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<Wishlist>,
                response: Response<Wishlist>
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


    fun getWishList(accestoken: String?, completion: (List<Wishlist>?, Error?) -> Unit) {
        api.listwish(accestoken).enqueue(object : Callback<List<Wishlist>> {
            override fun onFailure(call: Call<List<Wishlist>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<List<Wishlist>>,
                response: Response<List<Wishlist>>
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

    fun deletewish(accestoken: String?,id:Int, completion: (Wishlist?, Error?) -> Unit) {
        api.deletewish(accestoken,id).enqueue(object : Callback<Wishlist> {
            override fun onFailure(call: Call<Wishlist>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<Wishlist>,
                response: Response<Wishlist>
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