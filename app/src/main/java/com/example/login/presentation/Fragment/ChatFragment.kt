package com.example.login.presentation.Fragment

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
import com.example.login.network.sharedPreFerences.SharedPreFerences
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.concurrent.thread


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
        val roomName = arguments?.getString("roomName")
        var roomId = arguments?.getString("roomId")
        var userMail = SharedPreFerences(fragmentContext).dataUserMail!!
        stompClient.connect() //소켓 연결
        val data = JSONObject()
        data.put("type","ENTER")
        data.put("roomId",roomId)
        data.put("sender","HamTory")
        data.put("message","")
        stompClient.send("/pub/chat/send",data.toString()).subscribe()

        binding.messageButton.setOnClickListener {
            if(!binding.messageText.text.isNullOrEmpty()){
                SocketStompMessage(roomId!!,binding.messageText.text.toString(),userMail)
                binding.messageText.text = null
            }
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        mAdapter = MessageAdapter(mData)
        binding.recyclerview.adapter = mAdapter

        stompClient.topic("/sub/chat/user/"+"cksgur0612@dgsw.hs.kr").subscribe{//message 받는거
            Log.d("message.topic","$it")
            val stompMessage = it
            val payload = stompMessage.payload
            val gson = Gson()
            val messageData = gson.fromJson(payload, MessageData::class.java)
            Log.d("상태","${messageData.message}")
            mData.add(Message(messageData.message,false))
            GlobalScope.launch(Dispatchers.Main) {
                mAdapter.notifyDataSetChanged()
            }
        }


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("상태","onDestroyView")
        stompClient.disconnect() //소켓 연결 해제
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }

    private fun SocketStompMessage(roomId: String, text: String, mail: String){

        stompClient.lifecycle().subscribe{
            when(it.type){
                LifecycleEvent.Type.OPENED -> {
                    Log.d("상태","OPENED")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.d("상태","CLOSED")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.d("상태","ERROR")
                    Log.e("상태",it.exception.toString())
                    Toast.makeText(context, "error: ${it.exception}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d("상태",it.message)
                }
            }
        }
        val data = JSONObject()
        data.put("type","TALK")
        data.put("roomId",roomId)
        data.put("sender","HamTory")
        data.put("message",text)
        stompClient.send("/pub/chat/send",data.toString()).subscribe() //메시지 보내는것
    }
}