package com.example.secondhand.entity


import com.google.gson.annotations.SerializedName

data class History(

    @SerializedName("id")
    val id: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("image_url")
    val imageUrl: String
    )