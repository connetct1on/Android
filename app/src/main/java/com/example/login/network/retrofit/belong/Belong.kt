package com.example.login.network.retrofit.belong

import android.util.Log
import com.example.login.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.CreateBelongRequset
import com.example.login.network.retrofit.request.GetRoomRequset
import com.example.login.network.retrofit.request.GetUserRequset
import com.example.login.network.retrofit.response.GetRoomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Belong {
    fun BelongCreate(){
        RetrofitClient.api.belongCreate(CreateBelongRequset(user = String(), room = String())).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.code() == 200){
                    Log.d("상태","200")
                } else {
                    Log.d("상태","${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("상태","${t.message}")
            }

        })
    }
    fun GetRoom(){
        RetrofitClient.api.getRoom(GetRoomRequset(roomId = String())).enqueue(object : Callback<List<GetRoomResponse>>{
            override fun onResponse(
                call: Call<List<GetRoomResponse>>,
                response: Response<List<GetRoomResponse>>
            ) {
                if(response.code() == 200){
                    Log.d("상태","200")
                } else {
                    Log.d("상태","${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<GetRoomResponse>>, t: Throwable) {
                Log.d("상태","${t.message}")
            }

        })
    }
    fun GetRoomUserEmail(){
        RetrofitClient.api.getUser(GetUserRequset(email = String())).enqueue(object: Callback<List<GetRoomResponse>>{
            override fun onResponse(
                call: Call<List<GetRoomResponse>>,
                response: Response<List<GetRoomResponse>>
            ) {
                if(response.code() == 200){
                    Log.d("상태","200")
                } else {
                    Log.d("상태","${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<GetRoomResponse>>, t: Throwable) {
                Log.d("상태","${t.message}")
            }

        })
    }
}