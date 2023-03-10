package com.example.login.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class AuthUserGetResponse(
    @SerializedName("access")
    val access: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("idx")
    val idx: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String
)
