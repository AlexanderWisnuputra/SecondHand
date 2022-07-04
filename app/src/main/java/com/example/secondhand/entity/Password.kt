package com.example.secondhand.entity

import com.google.gson.annotations.SerializedName

data class Password(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: Int?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("city")
    val city: String?
)