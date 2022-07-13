package com.example.secondhand.entity

import com.google.gson.annotations.SerializedName

data class Password(
    @SerializedName("current_password")
    val passold: String?,
    @SerializedName("new_password")
    val newpass: String?,
    @SerializedName("confirm_password")
    val confirmpass: String?
)