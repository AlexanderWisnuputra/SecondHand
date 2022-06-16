package com.example.secondhand.dao

import androidx.lifecycle.ViewModel
import com.example.secondhand.entity.Login

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    // register
    fun userProfile(name: String, email: String, password: String, alamat: String, noHP: Int, picture: String) {
        val data = dataEntry(name, email, password, alamat, noHP, picture)
        insertToDatabase(data)
    }

    fun userProfile(id: Int,name: String, email: String, password: String, alamat: String, noHP: Int, picture: String): Int {
        val data = dataEntry(id, name, email, password, alamat, noHP, picture)
        return updateData(data)
    }

    private fun dataEntry(id: Int, name: String, email: String, password: String, alamat: String, noHP: Int, picture: String): Login {
        return Login(id, name, email, password, alamat, noHP, picture)
    }

    private fun dataEntry(name: String, email: String, password: String, alamat: String, noHP: Int, picture: String): Login {
        return Login(
            id = null, name, email, password, alamat, noHP, picture
        )
    }

    private fun insertToDatabase(data: Login) {
        userDao.registerUser(data)
    }

    fun checkUserExists(email: String, password: String): Boolean {
        return userDao.getUser(email, password)
    }

    fun getUserProfile(email: String?): Login {
        return userDao.getProfile(email)
    }

    fun getUserName(email: String): String {
        return userDao.getName(email)
    }

    private fun updateData(data: Login): Int {
        return userDao.updateData(data)
    }

    fun isInputEmpty(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Boolean {
        return !(name.isBlank() || email.isBlank() || password.isBlank() || passwordConfirm.isBlank())
    }

    fun isInputEmpty(
        email: String,
        password: String
    ): Boolean {
        return !(email.isBlank() || password.isBlank())
    }
}