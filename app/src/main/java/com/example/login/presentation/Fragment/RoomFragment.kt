package com.example.login.presentation.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.adapter.RoomAdapter
import com.example.login.databinding.FragmentRoomBinding
import com.example.login.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.CreateRoomRequest
import com.example.login.network.retrofit.response.CreateRoomResponse
import com.example.login.network.retrofit.response.FindRoomResponse
import com.example.login.network.sharedPreFerences.SharedPreFerences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private lateinit var mAdapter: RoomAdapter
private var mData: List<FindRoomResponse> = listOf(FindRoomResponse(0,"room","roomId"))
class RoomFragment : Fragment() {
    private var mbinding: FragmentRoomBinding ?= null
    private val binding get() = mbinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentRoomBinding.inflate(inflater, container, false)
        val view = binding.root
        RetrofitClient.api.findRoom(SharedPreFerences(requireContext()).dataBearerToken).enqueue(object : Callback<List<FindRoomResponse>>{
            override fun onResponse(
                call: Call<List<FindRoomResponse>>,
                response: Response<List<FindRoomResponse>>
            ) {
                if(response.code() == 200){
                    Log.d("상태","성공")
//                    mData = response.body()!!
//                    Log.d("상태","${mData}")
                } else{
                    Log.d("실패","실패 : ${response.code()} BearerToken: ${SharedPreFerences(requireContext()).dataBearerToken}")
                    mData = listOf(FindRoomResponse(1,"room","fdklsj"))
                }
            }

            override fun onFailure(call: Call<List<FindRoomResponse>>, t: Throwable) {
                Log.d("상태",t.message.toString())
            }

        })

        binding.addRoom.setOnClickListener {
            RetrofitClient.api.createRoom(SharedPreFerences(requireContext()).dataBearerToken,
                CreateRoomRequest(name = "n번방")
            ).enqueue(object : Callback<CreateRoomResponse>{
                override fun onResponse(
                    call: Call<CreateRoomResponse>,
                    response: Response<CreateRoomResponse>
                ) {
                    if(response.code() == 200){
                        Log.d("상태","성공 : ${response.body()?.roomId}")
                    } else {
                        Log.d("상태","실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CreateRoomResponse>, t: Throwable) {
                    Log.d("상태", t.message.toString())
                }

            })
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        mAdapter = RoomAdapter(mData)
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        binding.recyclerview.adapter = mAdapter

        return view
    }
}