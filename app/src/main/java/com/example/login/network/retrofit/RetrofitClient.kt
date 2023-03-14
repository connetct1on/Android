package com.example.login.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient{
    private const val url = "http://10.80.163.96:8080/"
    var server: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var api:API = server.create(API::class.java)
}


