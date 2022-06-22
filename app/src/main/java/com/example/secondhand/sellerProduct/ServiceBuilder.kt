package com.example.secondhand.sellerProduct

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    private const val URL ="https://market-final-project.herokuapp.com/"
    private var retrofit: Retrofit? = null
    private val okHttp =OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }.build()

    private fun getClient() : Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder().apply {
                client(okHttp)
                baseUrl(URL)
                addConverterFactory(GsonConverterFactory.create())
            }.build()
            return retrofit!!
        }
        return retrofit!!
    }
    //retrofit builder
  fun instance() = getClient().create(ServiceAPI::class.java)

}