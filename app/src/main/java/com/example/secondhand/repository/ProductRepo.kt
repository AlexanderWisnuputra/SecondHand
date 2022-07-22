package com.example.secondhand.repository

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

        fun categorybyId(id:Int, completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductCategory(id).enqueue(object : Callback<List<SellerProductItem>> {
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

    fun searchBar(search: String, completion: (List<SellerProductItem>?, Error?) -> Unit) {
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

     fun banner(completion: (List<Banner>?, Error?) -> Unit) {
        api.getBanner().enqueue(object : Callback<List<Banner>> {
            override fun onResponse(call: Call<List<Banner>?>, response: Response<List<Banner>>) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }

            override fun onFailure(call: Call<List<Banner>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }
        })
    }


    suspend fun addProuct(
        accestoken: String?,
        name: RequestBody,
        desc: RequestBody,
        baseprice: RequestBody,
        category: RequestBody,
        location: RequestBody,
        requestimage: MultipartBody.Part?,
    ): Response<Product> {
        return api.addProduct(
            access_token = accestoken,
            name = name,
            description = desc,
            basePrice = baseprice,
            categoryId = category,
            location = location,
            productImage = requestimage
        )
    }
}