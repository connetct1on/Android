package com.example.login.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class CreateRoomResponse(
    @SerializedName("name")
    val name: String
)