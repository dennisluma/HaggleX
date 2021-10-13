package com.dennisiluma.hagglex.utils

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferenceManager(private val context: Context)  {
    private val sharedPreferences = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)

    fun saveToken(token: String){
        sharedPreferences.edit().putString(USERTOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(USERTOKEN, null)
    }
}