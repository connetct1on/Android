package com.example.login.network.retrofit

import com.example.login.network.retrofit.request.CreateRoomRequest
import com.example.login.network.retrofit.request.LoginRequset
import com.example.login.network.retrofit.request.SignupRequest
import com.example.login.network.retrofit.response.CreateRoomResponse
import com.example.login.network.retrofit.response.FindRoomResponse
import com.example.login.network.retrofit.response.LoginResponse
import com.example.login.network.retrofit.response.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface API {
    @POST("api/chat/create")
    fun createRoom(
        @Header("Authorization") Authorization: String?,
        @Body body: CreateRoomRequest
    ): Call<CreateRoomResponse>

    @GET("api/room/find")
    fun findRoom(@Header("Authorization") Authorization: String?): Call<List<FindRoomResponse>>

    @POST("api/user/signup.do")
    fun signup(
        @Body body: SignupRequest
    ): Call<SignupResponse>

    @POST("api/user/login.do")
    fun login(
        @Body body: LoginRequset
    ): Call<LoginResponse>
}