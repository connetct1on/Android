package com.example.login.presentation.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.adapter.MessageAdapter
import com.example.login.databinding.FragmentChatBinding
import com.example.login.message.Message
import com.example.login.network.socket.WebSocketClient
import java.net.URI
import kotlin.concurrent.thread


class ChatFragment : Fragment() {

    //viewBinding
    private var mbinding: FragmentChatBinding ?= null
    private val binding get() = mbinding!!

    private val serverUri = URI.create("ws://220.94.98.54:7999/rt/chat")
    private val webSocketClient = WebSocketClient(serverUri)

    private lateinit var mAdapter: MessageAdapter
    private var mData = mutableListOf<Message>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root
        thread {
            webSocketClient.connect()
        }
        mData.add(Message("${binding.messageText}",""))
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        mAdapter = MessageAdapter(mData)
        binding.recyclerview.adapter = mAdapter

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
    }
}