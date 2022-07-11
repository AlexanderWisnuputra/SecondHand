package com.example.secondhand.entity

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String
)
