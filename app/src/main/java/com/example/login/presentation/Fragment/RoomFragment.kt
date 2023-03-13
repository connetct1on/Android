package com.example.login.presentation.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.login.adapter.RoomAdapter
import com.example.login.databinding.FragmentRoomBinding
import com.example.login.db.room.Database
import com.example.login.db.room.RoomRoom
import com.example.login.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.CreateRoomRequest
import com.example.login.network.retrofit.response.CreateRoomResponse
import com.example.login.network.retrofit.response.FindRoomResponse
import com.example.login.db.sharedPreFerences.SharedPreFerences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

private var mData: List<FindRoomResponse> = listOf(FindRoomResponse(0,0,"name","roomId0"))
private lateinit var mAdapter: RoomAdapter
class RoomFragment : Fragment() {

    //viewBinding
    private var mbinding: FragmentRoomBinding ?= null
    private val binding get() = mbinding!!

    //room Database
    private lateinit var db: Database
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentRoomBinding.inflate(inflater, container, false)
        val view = binding.root
        thread {
            findRoom()
        }
        db = Room.databaseBuilder(
            requireContext(),
            Database::class.java, "database"
        ).build()
        val getRoom = db.messageDao().getAllMessage()
        binding.addRoom.setOnClickListener { //방 추가
            addRoom()
        }

        binding.Refresh.setOnRefreshListener { //새로 고침
            findRoom()

            mAdapter = RoomAdapter(mData)
            binding.recyclerview.adapter = mAdapter
            mAdapter.notifyDataSetChanged()
            binding.Refresh.isRefreshing = false
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        mAdapter = RoomAdapter(mData)
        binding.recyclerview.addItemDecoration( //recyclerview 구분선
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        binding.recyclerview.adapter = mAdapter //어댑터 연결

        return view
    }

    fun findRoom(){ //방 찾는 함수
        RetrofitClient.api.findRoom(SharedPreFerences(requireContext()).dataBearerToken).enqueue(object : Callback<List<FindRoomResponse>>{
            override fun onResponse(
                call: Call<List<FindRoomResponse>>,
                response: Response<List<FindRoomResponse>>
            ) {
                if(response.code() == 200){
                    mData = response.body()!!
                    for(i in response.body()!!){
                        db.roomDao().insert(roomRoom = i) //error insert를 하려면 코루틴에서 해야된다고 한다
                    }

                } else{
                    Log.d("실패","실패 : ${response.code()} BearerToken: ${SharedPreFerences(requireContext()).dataBearerToken}")
                }
            }

            override fun onFailure(call: Call<List<FindRoomResponse>>, t: Throwable) {
                Log.d("상태",t.message.toString())
            }

        })
    }
    fun addRoom() { //방 만드는 함수
        RetrofitClient.api.createRoom(
            SharedPreFerences(requireContext()).dataBearerToken,
            CreateRoomRequest(name = "i번방")
        ).enqueue(object : Callback<CreateRoomResponse> {
            override fun onResponse(
                call: Call<CreateRoomResponse>,
                response: Response<CreateRoomResponse>
            ) {
                if (response.code() == 200) {
                } else {
                    Log.d("상태", "실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CreateRoomResponse>, t: Throwable) {
                Log.d("상태", t.message.toString())
            }

        })
    }

}