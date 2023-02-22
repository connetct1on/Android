package com.example.login.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class FindRoomResponse(
    @SerializedName("access")
    val access: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("roomId")
    val roomId: String
)