package com.example.secondhand.sellerProduct

import android.content.ClipDescription
import com.example.secondhand.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("seller/product")
    suspend fun addProduct(
        @Header("access_token") access_token:String?,
        @Part ("name") name: RequestBody,
        @Part ("description") description: RequestBody,
        @Part ("base_price") basePrice: RequestBody,
        @Part ("category_ids") categoryId: RequestBody,
        @Part ("location") location: RequestBody,
        @Part productImage: MultipartBody.Part
    ): Response<Product>

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

    @PUT("auth/change-password")
    suspend fun changePass(
        @Header("access_token") access_token:String?,
        @Body post: Password
    ): Response<Password>
}
