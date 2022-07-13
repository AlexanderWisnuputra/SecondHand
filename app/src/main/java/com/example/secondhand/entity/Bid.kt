package com.example.secondhand.entity

import com.google.gson.annotations.SerializedName

data class Bid(
    @SerializedName("product_id")
    val product_id: Int,
    @SerializedName("bid_price")
    val bid_price: Int
)