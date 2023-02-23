package com.example.login.presentation.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.adapter.MessageAdapter
import com.example.login.databinding.FragmentChatBinding
import com.example.login.message.Message
import com.example.login.network.sharedPreFerences.SharedPreFerences
import com.example.login.network.socket.WebSocketClient
import org.json.JSONObject
import java.net.URI
import kotlin.concurrent.thread


class ChatFragment : Fragment() {

    private lateinit var fragmentContext: Context

    //viewBinding
    private var mbinding: FragmentChatBinding ?= null
    private val binding get() = mbinding!!

    private val serverUri = URI.create("ws://220.94.98.54:7999/rt/chat")



    private lateinit var mAdapter: MessageAdapter
    private var mData = mutableListOf<Message>()

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
        val headers = HashMap<String, String>()
        headers["Authorization"] = SharedPreFerences(fragmentContext).dataBearerToken.toString()
        Log.d("상태","$headers")
        val webSocketClient = WebSocketClient(serverUri, headers)
        thread {
            webSocketClient.connect()
        }
        binding.messageButton.setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("type","TALK")
            jsonObject.put("roomId","34ce07dd-7c66-4d5c-ae77-2544fb35c875")
            jsonObject.put("sender","HamTory")
            jsonObject.put("message","${binding.messageText}")

            val message = jsonObject.toString()
            webSocketClient.send(message)
            mData.add(Message("${binding.messageText}",""))

        }
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        mAdapter = MessageAdapter(mData)
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        binding.recyclerview.adapter = mAdapter

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }
}