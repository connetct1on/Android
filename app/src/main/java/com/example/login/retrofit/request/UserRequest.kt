package com.example.login.retrofit.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("access")
    val access: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("roomId")
    val roomId: String
)
