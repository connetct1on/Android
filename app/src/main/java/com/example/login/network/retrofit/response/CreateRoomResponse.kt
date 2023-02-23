package com.example.login.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class CreateRoomResponse(
    @SerializedName("access")
    val access: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("roomId")
    val roomId: String
)