package com.example.login.message.createRoom

import android.util.Log
import com.example.login.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.CreateRoomRequest
import com.example.login.network.retrofit.response.CreateRoomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateRoom {
    fun createRoom(){
        val call = RetrofitClient.api.createRoom(CreateRoomRequest(name = String()))
        call.enqueue(object : Callback<CreateRoomResponse>{
            override fun onResponse(
                call: Call<CreateRoomResponse>,
                response: Response<CreateRoomResponse>
            ) {
                if(response.code() == 200){
                    Log.d("상태","성공 :${response.body()}")
                } else{
                    Log.d("상태","실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CreateRoomResponse>, t: Throwable) {
                Log.d("상태", t.message.toString())
            }

        })
    }
}