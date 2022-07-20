package com.example.secondhand.repository

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepo {
    private val api = ServiceBuilder.instance()
    fun getNotification(accestoken: String?, completion: (List<Notification>?, Error?) -> Unit) {
        api.notif(accestoken).enqueue(object : Callback<List<Notification>> {
            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<List<Notification>>,
                response: Response<List<Notification>>
            ) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }
        })
    }

    fun getNotificationbyUser(accestoken: String?,type:String, completion: (List<Notification>?, Error?) -> Unit) {
        api.notifbyUser(accestoken,type).enqueue(object : Callback<List<Notification>> {
            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<List<Notification>>,
                response: Response<List<Notification>>
            ) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }
        })
    }




    fun patchNotification(accestoken: String?,id:Int, completion: (Notification?, Error?) -> Unit) {
        api.notifPatch(accestoken,id).enqueue(object : Callback<Notification> {
            override fun onFailure(call: Call<Notification>, t: Throwable) {
                println(t.message)
                completion(null, Error(t.message))
            }

            override fun onResponse(
                call: Call<Notification>,
                response: Response<Notification>
            ) {
                when {
                    response.isSuccessful -> {
                        completion(response.body(), null)
                    }
                    !response.isSuccessful -> {
                        completion(null, Error("Cannot get data"))
                    }
                }
            }
        })
    }
}
