package com.example.login.presentation.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.login.databinding.FragmentChatBinding
import com.example.login.network.socket.WebSocketClient
import java.net.URI


class ChatFragment : Fragment() {

    //viewBinding
    private var mbinding: FragmentChatBinding ?= null
    private val binding get() = mbinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root
        val serverUri = URI.create("ws://220.94.98.54:7999/rt/chat")
        val webSocketClient = WebSocketClient(serverUri)
        webSocketClient.connect()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null

    }
}