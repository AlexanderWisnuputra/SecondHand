package com.example.secondhand.sellerProduct

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

    @GET("buyer/product/{id}")
    fun getProductSoldbyID (@Path("id") userid:String) : Call<SellerProductItem>

    @POST("auth/register")
    suspend fun addUser(
        @Body post: User
    ): Response<User>

    @POST("auth/login")
    fun loginUser(
        @Body post: UserAcessToken
    ): Call<UserAcessToken>

    @PUT("auth/user")
    fun changeDetail(
        @Path("access_token") access_token:String,
        @Body post:User
    ): Call<User>
}
