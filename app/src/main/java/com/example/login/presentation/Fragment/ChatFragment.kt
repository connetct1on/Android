package com.example.login.presentation.Fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.adapter.MessageAdapter
import com.example.login.databinding.FragmentChatBinding
import com.example.login.message.Message
import com.example.login.message.MessageData
import com.example.login.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.response.AuthUserGetResponse
import com.example.login.network.sharedPreFerences.SharedPreFerences
import com.google.gson.Gson
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.naiksoftware.stomp.dto.StompHeader


class ChatFragment : Fragment() {

    private lateinit var fragmentContext: Context

    //viewBinding
    private var mbinding: FragmentChatBinding ?= null
    private val binding get() = mbinding!!

    private lateinit var mAdapter: MessageAdapter
    private var mData = mutableListOf<Message>()

    private val serverUri = "ws://220.94.98.54:7999/ws"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, serverUri)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root
        val roomName = arguments?.getString("roomName") //방이름
        var roomId = arguments?.getString("roomId") //roomId
        var Name: String? = null
        userGet { responseName ->
            Name = responseName
        }
        val headerList = arrayListOf<StompHeader>()
        headerList.add(StompHeader("Authorization",SharedPreFerences(fragmentContext).dataBearerToken))
        stompClient.connect(headerList) //소켓 연결
        val data = JSONObject()
        data.put("type","ENTER")
        data.put("roomId",roomId)
        data.put("sender",Name)
        data.put("message","")

        stompClient.send("/pub/chat/send",data.toString()).subscribe()

        binding.messageButton.setOnClickListener {
            if(!binding.messageText.text.isNullOrEmpty()){
                SocketStompMessage(roomId!!,binding.messageText.text.toString(),Name!!)
                binding.messageText.text = null
            }
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        mAdapter = MessageAdapter()
        binding.recyclerview.adapter = mAdapter

        stompClient.topic("/sub/chat/user/"+"cksgur0612@dgsw.hs.kr").subscribe{
            val stompMessage = it
            Log.d("상태","$it")
            val payload = stompMessage.payload
            val gson = Gson()
            val messageData = gson.fromJson(payload, MessageData::class.java)//message 받는거


            Log.d("상태","${messageData.time}")
            mAdapter.addData(Message(messageData.message,messageData.sender,messageData.type,messageData.time))
            activity?.runOnUiThread{
                mAdapter.notifyDataSetChanged()
            }
        }


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        stompClient.disconnect() //소켓 연결 해제
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }

    private fun SocketStompMessage(
        roomId: String,
        text: String,
        userName: String,
    ){

        stompClient.lifecycle().subscribe{거
            when(it.type){
                LifecycleEvent.Type.OPENED -> {
                    Log.d(TAG,"OPENED")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.d(TAG,"CLOSED")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.d(TAG,"ERROR")
                    Log.e(TAG,it.exception.toString())
                    Toast.makeText(context, "error: ${it.exception}", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
        val data = JSONObject()
        data.put("type","TALK")
        data.put("roomId",roomId)
        data.put("sender",userName)
        data.put("message",text)
        stompClient.send("/pub/chat/send",data.toString()).subscribe() //메시지 보내는것
    }

    fun userGet(callback: (String) -> Unit){
        var userName: String
        RetrofitClient.api.authGetUser(Authorization = SharedPreFerences(fragmentContext).dataBearerToken,
            email = SharedPreFerences(fragmentContext).dataUserMail!!
        ).enqueue(object : Callback<AuthUserGetResponse>{
            override fun onResponse(
                call: Call<AuthUserGetResponse>,
                response: Response<AuthUserGetResponse>
            ) {
                if(response.code() == 200){
                    userName = response.body()?.name.toString()
                    callback(userName)
                }
            }
            override fun onFailure(call: Call<AuthUserGetResponse>, t: Throwable) {
                Log.d("상태","${t.message}")
                callback("")
            }
        })
    }
}