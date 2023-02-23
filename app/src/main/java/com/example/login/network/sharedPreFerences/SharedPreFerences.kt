package com.example.login.network.sharedPreFerences

import android.content.Context
import android.content.SharedPreferences

class SharedPreFerences(context : Context) {
    private val prefsFile = "a"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFile,0)

    var dataAccessToken: String?
        get() = prefs.getString("AccessToken","")
        set(value) = prefs.edit().putString("AccessToken",value).apply()

    var dataRefreshToken: String?
        get() = prefs.getString("RefreshToken","")
        set(value) = prefs.edit().putString("RefreshToken",value).apply()
}