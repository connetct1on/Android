package com.example.login.retrofit

import com.example.socket.retrofit.request.CreateRoomRequest
import com.example.socket.retrofit.request.FindRoomRequest
import com.example.socket.retrofit.request.SignupRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @POST("/chat/create")
    fun createRoom(
        @Body body: CreateRoomRequest
    ): Call<CreateRoomRequest>

    @GET("/chat/find")
    fun findRoom(): Call<FindRoomRequest>

    @POST("/user/signup.do")
    fun signup(
        @Body body: SignupRequest
    ): Call<SignupRequest>
}