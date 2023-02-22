package com.example.socket.retrofit.request

import com.google.gson.annotations.SerializedName

data class FindRoomRequest(
    @SerializedName("access")
    val access: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("roomId")
    val roomId: String
)
