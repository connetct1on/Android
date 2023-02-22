package com.example.login.network.retrofit.request

import com.google.gson.annotations.SerializedName

data class CreateRoomRequest(
    @SerializedName("name")
    val name: String,
)
