package com.example.secondhand.repository

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.Bid
import com.example.secondhand.entity.BidStatus
import com.example.secondhand.entity.History
import com.example.secondhand.entity.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SOrderRepo {
    private val api = ServiceBuilder.instance()

    fun productList(accesstoken: String?,completion: (List<Product>?, Error?) -> Unit) {
        api.productSold(accesstoken).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }
        })
    }

    fun productListbyid(accesstoken: String?, id:Int,completion: (Product?, Error?) -> Unit) {
        api.getproductsoldbyID(accesstoken,id).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }
        })
    }



}