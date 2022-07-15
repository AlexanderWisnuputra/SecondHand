package com.example.secondhand.entity


import com.google.gson.annotations.SerializedName

data class Wishlist(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("user_id")
    val userId: Int
)