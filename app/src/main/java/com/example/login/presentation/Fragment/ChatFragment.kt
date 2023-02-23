package com.example.login.presentation.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.login.R
import com.example.login.databinding.FragmentChatBinding
import io.socket.client.Socket


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
        mSocket.connect()


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mbinding = null
        mSocket.disconnect()
    }
}