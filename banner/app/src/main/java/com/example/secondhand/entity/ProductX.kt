package com.example.secondhand.entity


import com.google.gson.annotations.SerializedName

data class ProductX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("status")
    val status: String
)