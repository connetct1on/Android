package com.example.login.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class GetRoomResponse(
    @SerializedName("room")
    val room: String,
    @SerializedName("user")
    val user: String
)
