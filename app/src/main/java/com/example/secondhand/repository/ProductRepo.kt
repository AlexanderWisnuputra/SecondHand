package com.example.secondhand.repository

import com.example.secondhand.entity.Product
import com.example.secondhand.entity.SellerProductItem
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.Category
import com.example.secondhand.entity.History
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

    fun category(completion: (List<Category>?, Error?) -> Unit) {
        api.category().enqueue(object : Callback<List<Category>> {
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
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

    fun category1(completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductCategory(96).enqueue(object : Callback<List<SellerProductItem>> {
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

    fun Category2(completion: (List<SellerProductItem>?, Error?) -> Unit) {
        api.getProductCategory(114).enqueue(object : Callback<List<SellerProductItem>> {
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
        api.getProductCategory(105).enqueue(object : Callback<List<SellerProductItem>> {
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
        api.getProductCategory(119).enqueue(object : Callback<List<SellerProductItem>> {
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

    fun getByID(id: Int, completion: (SellerProductItem?, Error?) -> Unit) {
        api.getProductSoldbyID(id).enqueue(object : Callback<SellerProductItem> {
            override fun onFailure(call: Call<SellerProductItem>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<SellerProductItem>,
                response: Response<SellerProductItem>
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