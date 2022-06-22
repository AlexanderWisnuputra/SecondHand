package com.example.secondhand.sellerProduct.bprepository

import com.example.secondhand.entity.SellerProductItem
import com.example.secondhand.entity.User
import com.example.secondhand.sellerProduct.ServiceAPI
import com.example.secondhand.sellerProduct.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepo {
    private val api = ServiceBuilder.instance()

    fun producFunc(completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductSold().enqueue(object : Callback<List<SellerProductItem>> {
            override fun onFailure(call: Call<List<SellerProductItem>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }
            override fun onResponse(
                call: Call<List<SellerProductItem>>,
                response: Response<List<SellerProductItem>>
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
