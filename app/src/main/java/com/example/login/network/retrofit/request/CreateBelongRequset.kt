package com.example.login.network.retrofit.request

import com.google.gson.annotations.SerializedName

data class CreateBelongRequset(
    @SerializedName("user")
    val user: String,
    @SerializedName("room")
    val room: String
)