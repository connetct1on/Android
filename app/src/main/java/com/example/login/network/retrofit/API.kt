package com.example.login.network.retrofit

import com.example.login.network.retrofit.request.*
import com.example.login.network.retrofit.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
    @POST("api/room/create")
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

    @POST("api/belong/create")
    fun belongCreate(
        @Body body: CreateBelongRequset
    ): Call<Void>

    @GET("api/belong/get/Room")
    fun getRoom(
        @Body body: GetRoomRequset
    ): Call<List<GetRoomResponse>>

    @GET("api/belong/get/User")
    fun getUser(
        @Body body: GetUserRequset
    ): Call<List<GetRoomResponse>>

    @POST("api/auth/user/get")
    fun authGetUser(
        @Header("Authorization") Authorization: String?,
        @Query("email") email: String
    ): Call<AuthUserGetResponse>
}