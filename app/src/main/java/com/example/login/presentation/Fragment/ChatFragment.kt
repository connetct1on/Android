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

        val url = "ws"

        binding.messageButton.setOnClickListener {

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