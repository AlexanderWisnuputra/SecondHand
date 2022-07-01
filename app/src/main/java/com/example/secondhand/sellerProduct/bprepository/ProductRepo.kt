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

    fun categoryKursus(completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductCategory1(38).enqueue(object : Callback<List<SellerProductItem>> {
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
    fun categorySport(completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductCategory2(39).enqueue(object : Callback<List<SellerProductItem>> {
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
    fun categoryMakanan(completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductCategory3(40).enqueue(object : Callback<List<SellerProductItem>> {
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
    fun categoryhobi(completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductCategory4(81).enqueue(object : Callback<List<SellerProductItem>> {
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

    fun searchBar(search :String,completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductSearchBar(search).enqueue(object : Callback<List<SellerProductItem>> {
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
