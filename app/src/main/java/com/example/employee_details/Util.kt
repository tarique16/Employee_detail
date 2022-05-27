package com.example.employee_details

import android.content.Context
import android.content.SharedPreferences

class Util {
    companion object{
        var SHARE_PREFERENCE_NAME = "EMPLOYEE_DETAILS"
        var USER_NAME = "USER_NAME"
        var PASSWORD = "PASSWORD"

        fun getUserName(context: Context): String {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val sharedNameValue = sharedPreferences.getString(USER_NAME, "never")
            return sharedNameValue!!
        }

        fun setUserName(context: Context, username: String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(USER_NAME,username)
            editor.apply()
        }

        fun getPassword(context: Context): String {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val sharedNameValue = sharedPreferences.getString(PASSWORD, "never")
            return sharedNameValue!!
        }

        fun setPassword(context: Context, password: String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(PASSWORD,password)
            editor.apply()
        }
    }
}