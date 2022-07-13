package com.example.secondhand.entity

import com.google.gson.annotations.SerializedName

class BidStatus (
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productID: Int,
    @SerializedName("buyer_id")
    val buyerID: Int,
    @SerializedName("price")
    val priceID: Int,
    @SerializedName("product_name")
    val name: String,
    @SerializedName("base_price")
    val normalPrice: Int,
    @SerializedName("image_product")
    val img: String,
    @SerializedName("status")
    val status: String
)