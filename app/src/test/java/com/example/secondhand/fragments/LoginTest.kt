package com.example.secondhand.fragments

import com.example.secondhand.api.ServiceBuilder
import com.example.secondhand.entity.UserAcessToken
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginTest {


    @Test
    fun wrongGetUser(){
        val email = "testing@email.com"
        val password = "testing123"
        val masuk = ServiceBuilder.instance().loginUser(UserAcessToken( email, password,null))
        masuk
    }
}