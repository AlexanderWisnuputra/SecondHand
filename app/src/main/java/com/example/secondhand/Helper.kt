package com.example.secondhand

import android.content.Context
import android.content.SharedPreferences

class Helper(context: Context) {

    private val sharedPrefFile = "userPreference"
    private var sharedPref: SharedPreferences =
        context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun putEmail(key: String, value: String) {
        editor.putString(key, value).apply()
    }
    fun getEmail(key: String): String? {
        return sharedPref.getString(key, null)
    }
    fun putAT(key: String, value: String) {
        editor.putString(key, value).apply()
    }
    fun getAT(key: String): String? {
        return sharedPref.getString(key, null)
    }
    fun putSell(key: String, value: String) {
        editor.putString(key, value).apply()
    }
    fun getSell(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun putFilter(key: String, value: String) {
        editor.putString(key, value).apply()
    }
    fun getFilter(key: String): String? {
        return sharedPref.getString(key, null)
    }
}