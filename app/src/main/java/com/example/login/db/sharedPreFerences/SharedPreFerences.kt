package com.example.login.db.sharedPreFerences

import android.content.Context
import android.content.SharedPreferences
import com.example.login.message.MessageData

class SharedPreFerences(context : Context) {
    private val prefsFile = "a"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFile,0)

    var dataAccessToken: String?
        get() = prefs.getString("AccessToken","")
        set(value) = prefs.edit().putString("AccessToken",value).apply()

    var dataRefreshToken: String?
        get() = prefs.getString("RefreshToken","")
        set(value) = prefs.edit().putString("RefreshToken",value).apply()

    var dataBearerToken: String?
        get() = prefs.getString("BearerToken","")
        set(value) = prefs.edit().putString("BearerToken",value).apply()
    var dataUserMail: String?
        get() = prefs.getString("UserMail","")
        set(value) = prefs.edit().putString("UserMail",value).apply()
    var dataUserName: String?
        get() = prefs.getString("UserName","")
        set(value) = prefs.edit().putString("UserName",value).apply()
}