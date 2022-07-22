package com.example.secondhand.api

import com.example.secondhand.entity.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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

    // SELLER PRODUCT put
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
    fun productSold(@Header("access_token") access_token:String?): Call<List<ProductResponse>>

    @GET("seller/product/{id}")
    fun getproductsoldbyID (@Header("access_token") access_token:String?, @Path("id") id : Int) : Call<ProductResponse>

    @FormUrlEncoded
    @PATCH("seller/product/{id}")
    fun putProduct (@Header("access_token") access_token:String?, @Path("id") id : Int,
                    @Part ("name") name: RequestBody,
                    @Part ("description") description: RequestBody,
                    @Part ("base_price") basePrice: RequestBody,
                    @Part ("category_ids") categoryId: RequestBody,
                    @Part ("location") location: RequestBody,
                    @Part productImage: MultipartBody.Part?
    ) : Call<Product>


    @FormUrlEncoded
    @PATCH("seller/product/{id}")
    fun patchProduct (@Header("access_token") access_token:String?, @Path("id") id : Int, @Field("status") status: String) : Call<ProductResponse>

    @DELETE("seller/product/{id}")
    fun deleteProduct (@Header("access_token") access_token:String?, @Path("id") id : Int) : Call<Void>

    // SELLER ORDER N
    @GET("seller/order")
    fun getordered (@Header("access_token") access_token:String?, @Query("status") status:String) : Call<List<BidStatus>>

    @GET("seller/order/{id}")
    fun getorderedbyid (@Header("access_token") access_token:String?, @Path("id") id:Int) : Call<BidStatus>

    @FormUrlEncoded
    @PATCH("seller/order/{id}")
    fun patchstatus (@Header("access_token") access_token:String?,@Path("id") id:Int, @Field("status") status: String) : Call<BidStatus>

    @GET("seller/order/{product_id}")
    fun getorderedbyproductid (@Header("access_token") access_token:String?, @Path("product_id") id:Int) : Call<BidStatus>

    // SELLER CATEGORY NN
    @GET("seller/category/")
    fun category () : Call<List<Category>>

    @GET("seller/category/")
    fun categorybyID (@Query("id") id:Int) : Call<Category>


    // SELLER BANNER ☆
    @GET("seller/banner")
    fun getBanner(): Call<List<Banner>>

    // BUYER PRODUCT ☆
    @GET("buyer/product")
    fun getProductSold () : Call<List<SellerProductItem>>

    @GET("buyer/product/{id}")
    fun getProductSoldbyID (@Path("id") id:Int) : Call<SellerProductItem>

    @GET("buyer/product/")
    fun getProductSearchBar (@Query("search") search:String) : Call<List<SellerProductItem>>

    @GET("buyer/product/")
    fun getProductCategory (@Query("category_id") search:Int) : Call<List<SellerProductItem>>

    // BUYER ORDER ☆
    @POST("buyer/order")
    fun bidPrice (@Header("access_token") access_token:String?, @Body post: Bid) : Call<Bid>

    // HISTORY ☆
    @GET("history")
    fun getHistory (@Header("access_token") access_token:String?) : Call<List<History>>

    @GET("history/{id}")
    fun getHistorybyID (@Header("access_token") access_token:String?, @Path("id") id:Int) : Call<History>

    // NOTIFICATION patch error
    @GET("notification")
    fun notif (@Header("access_token") access_token:String?) : Call<List<Notification>>

    @GET("notification/{id}")
    fun notifbyID (@Header("access_token") access_token:String?,@Path("id") id:Int) : Call<Notification>

    @GET("notification/")
    fun notifbyUser (@Header("access_token") access_token:String?,@Query("notification_type") type:String) : Call<List<Notification>>

    @PATCH("notification/{id}")
    fun notifPatch (@Header("access_token") access_token:String?,@Path("id") id:Int) : Call<Notification>

}
