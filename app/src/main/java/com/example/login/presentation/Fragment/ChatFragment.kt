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
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.net.URI
import kotlin.concurrent.thread


class ChatFragment : Fragment() {

    private lateinit var fragmentContext: Context

    //viewBinding
    private var mbinding: FragmentChatBinding ?= null
    private val binding get() = mbinding!!


    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable

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
        val serverUri = "ws://220.94.98.54:7999/ws"
        val intervalMillis = 1000L
        val client = OkHttpClient()

        val stomp = StompClient(client,intervalMillis)

        //connect
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    Log.d("상태","OPENED")
                }
                Event.Type.CLOSED -> {
                    Log.d("상태","CLOSED")
                }
                Event.Type.ERROR -> {
                    Log.d("상태","ERROR")
                }
                else -> {}
            }
        }


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