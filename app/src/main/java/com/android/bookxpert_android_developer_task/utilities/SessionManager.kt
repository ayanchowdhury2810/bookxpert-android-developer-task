package com.android.bookxpert_android_developer_task.utilities

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("is_login_complete", Context.MODE_PRIVATE)

    fun saveIsLoginCompletedData(key: String, value: Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getIsLoginCompletedData(key: String, defaultValue: Boolean): Boolean{
        return sharedPreferences.getBoolean(key, defaultValue) ?: defaultValue
    }

}