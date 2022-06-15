package com.example.secondhand.sellerProduct

import retrofit2.Call
import retrofit2.http.GET

interface SellerProductList {
    @GET("Produk")
    fun getProductSold () : Call<List<SellerProductItem>>
}
