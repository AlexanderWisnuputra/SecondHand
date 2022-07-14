package com.example.secondhand.api

import com.example.secondhand.entity.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ServiceAPI {

    // AUTH ☆
    @POST("auth/register")
    suspend fun addUser(
        @Body post: User
    ): Response<User>

    @POST("auth/login")
    fun loginUser(
        @Body post: UserAcessToken
    ): Call<UserAcessToken>

    @GET("auth/user")
    fun getUser(@Header("access_token") access_token:String?): Call<User>

    @Multipart
    @PUT("auth/user")
    suspend fun changeDetail(
        @Header("access_token") access_token:String?,
        @Part ("full_name") name: RequestBody,
        @Part ("email") email: RequestBody,
        @Part ("password") password: RequestBody,
        @Part ("phone_number") phone_number: RequestBody,
        @Part ("address") address: RequestBody,
        @Part ("city") city: RequestBody,
        @Part productImage: MultipartBody.Part?
    ): Response<User>

    @PUT("auth/change-password")
    suspend fun changePass(
        @Header("access_token") access_token:String?,
        @Body post: Password
    ): Response<Password>

    // SELLER PRODUCT ☒☒☒
    @Multipart
    @POST("seller/product")
    suspend fun addProduct(
        @Header("access_token") access_token:String?,
        @Part ("name") name: RequestBody,
        @Part ("description") description: RequestBody,
        @Part ("base_price") basePrice: RequestBody,
        @Part ("category_ids") categoryId: RequestBody,
        @Part ("location") location: RequestBody,
        @Part productImage: MultipartBody.Part?
    ): Response<Product>

    @GET("seller/product")
    fun productSold(@Header("access_token") access_token:String?): Call<List<Product>>

    @GET("seller/product")
    fun getproductsoldbyID (@Header("access_token") access_token:String?, @Query("id") id : Int) : Call<Product>

    // SELLER ORDER ☒☒☒☒


    // SELLER CATEGORY ☒☒
    @GET("seller/category/")
    fun category () : Call<List<Category>>

    @GET("seller/category/")
    fun categorybyID (@Query("id") id:Int) : Call<Category>


    // SELLER BANNER ☆
    @GET("seller/banner")
    suspend fun getBanner(): Response<Banner>

    // BUYER PRODUCT ☆
    @GET("buyer/product")
    fun getProductSold () : Call<List<SellerProductItem>>

    @GET("buyer/product/")
    fun getProductSoldbyID (@Query("id") id:Int) : Call<SellerProductItem>

    @GET("buyer/product/")
    fun getProductSearchBar (@Query("search") search:String) : Call<List<SellerProductItem>>

    @GET("buyer/product/")
    fun getProductCategory (@Query("category_id") search:Int) : Call<List<SellerProductItem>>

    // BUYER ORDER ☒☒☒☒
    @POST("buyer/order")
    fun bidPrice (@Header("access_token") access_token:String?, @Body post: Bid) : Call<Bid>

    @GET("buyer/order")
    fun getOrder (@Header("access_token") access_token:String?) : Call<List<BidStatus>>

    @GET("buyer/order/")
    fun getOrderbyID (@Header("access_token") access_token:String?, @Query("id") id : Int) : Call<BidStatus>

    @PUT("buyer/order/")
    fun patchOrder (@Header("access_token") access_token:String?, @Query("id") id : Int) : Call<BidStatus>

    @DELETE("buyer/order/")
    fun deleteOrder (@Header("access_token") access_token:String?, @Query("id") id : Int) : Call<Bid>

    // BUYER WISHLIST ☒☒☒☒



    // HISTORY ☆
    @GET("history")
    fun getHistory (@Header("access_token") access_token:String?) : Call<List<History>>

    @GET("history")
    fun getHistorybyID (@Header("access_token") access_token:String?, @Query("id") id:Int) : Call<History>

    // NOTIFICATION ☒
    @GET("notification")
    fun notif (@Header("access_token") access_token:String?) : Call<List<Notification>>

    @GET("notification/")
    fun notifbyID (@Header("access_token") access_token:String?,@Query("id") id:Int) : Call<Notification>

    @PATCH("notification/")
    fun notifPatch (@Header("access_token") access_token:String?,@Query("id") id:Int) : Call<Notification>

}
