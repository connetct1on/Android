package com.example.login.network.retrofit.request

import com.google.gson.annotations.SerializedName

data class GetUserRequset(
    @SerializedName("email")
    val email: String
)
