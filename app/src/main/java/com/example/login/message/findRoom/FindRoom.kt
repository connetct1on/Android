package com.example.login.message.findRoom

import android.content.Context
import android.util.Log
import com.example.login.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.response.FindRoomResponse
import com.example.login.network.sharedPreFerences.SharedPreFerences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindRoom {

    companion object{
        lateinit var context: Context
    }
    fun findRoom(){
        RetrofitClient.api.findRoom(SharedPreFerences(context).dataAccessToken).enqueue(object : Callback<List<FindRoomResponse>>{
            override fun onResponse(
                call: Call<List<FindRoomResponse>>,
                response: Response<List<FindRoomResponse>>
            ) {
                if(response.code() == 200){
                    Log.d("상태","성공")
                    val room: List<FindRoomResponse>? = response.body()
                } else{
                    Log.d("실패","실패 : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<FindRoomResponse>>, t: Throwable) {
                Log.d("상태",t.message.toString())
            }

        })
    }
}
