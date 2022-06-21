package com.example.secondhand.sellerProduct

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductList {
    @GET("buyer/product")
    fun getProductSold () : Call<List<SellerProductItem>>

    @GET("seller/product/{id}")
    fun getProductSoldbyID (@Path("id") userid:String) : Call<SellerProductItem>
}
