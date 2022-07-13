package com.example.secondhand.repository

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.Product
import com.example.secondhand.entity.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part

class ChangeAccRepo {
    private val api = ServiceBuilder.instance()

    suspend fun changeAcc(
        accestoken: String?,
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        requestimage: MultipartBody.Part?
        ): Response<User> {
        return api.changeDetail(
            access_token = accestoken,
            name = name,
            email = email,
            password = password,
            phone_number = phone_number,
            address = address,
            city = city,
            productImage = requestimage

        )
    }
}