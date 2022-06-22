package com.example.secondhand.entity


import com.google.gson.annotations.SerializedName

data class UserAcessToken(

    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("access_token")
    val accessToken: String?

)