package com.example.secondhand.repository

import android.widget.Toast
import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SOrderRepo {
    private val api = ServiceBuilder.instance()

    fun productList(accesstoken: String?,completion: (List<ProductResponse>?, Error?) -> Unit) {
        api.productSold(accesstoken).enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(call: Call<List<ProductResponse>>, response: Response<List<ProductResponse>>) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }
        })
    }



    fun patch(accestoken: String?,id:Int,status: String, completion: (ProductResponse?, Error?) -> Unit) {
        api.patchProduct(accestoken,id,status).enqueue(object : Callback<ProductResponse> {
            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
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
