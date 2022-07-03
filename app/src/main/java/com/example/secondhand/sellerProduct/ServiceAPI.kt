package com.example.secondhand.sellerProduct

import com.example.secondhand.entity.Login
import com.example.secondhand.entity.SellerProductItem
import com.example.secondhand.entity.User
import com.example.secondhand.entity.UserAcessToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ServiceAPI {
    @GET("buyer/product")
    fun getProductSold () : Call<List<SellerProductItem>>

    @GET("buyer/product/")
    fun getProductSoldbyID (@Query("id") id:Int) : Call<SellerProductItem>

    @GET("buyer/product/")
    fun getProductSearchBar (@Query("search") search:String) : Call<List<SellerProductItem>>

    @GET("buyer/product/")
    fun getProductCategory1 (@Query("category_id") search:Int) : Call<List<SellerProductItem>>

    @GET("buyer/product/")
    fun getProductCategory2 (@Query("category_id") search:Int) : Call<List<SellerProductItem>>

    @GET("buyer/product/")
    fun getProductCategory3 (@Query("category_id") search:Int) : Call<List<SellerProductItem>>

    @GET("buyer/product/")
    fun getProductCategory4 (@Query("category_id") search:Int) : Call<List<SellerProductItem>>


    @POST("auth/register")
    suspend fun addUser(
        @Body post: User
    ): Response<User>

    @POST("auth/login")
    fun loginUser(
        @Body post: UserAcessToken
    ): Call<UserAcessToken>


    @PUT("auth/user")
    suspend fun changeDetail(
        @Header("access_token") access_token:String?,
        @Body post: User
    ): Response<User>

}
