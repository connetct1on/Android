package com.example.login.presentation.Fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.window.OnBackInvokedCallback
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.login.MainActivity
import com.example.login.R
import com.example.login.adapter.MessageAdapter
import com.example.login.databinding.FragmentChatBinding
import com.example.login.db.room.Database
import com.example.login.db.room.dao.MessageDao
import com.example.login.message.Message
import com.example.login.message.MessageData
import com.example.login.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.response.AuthUserGetResponse
import com.example.login.db.sharedPreFerences.SharedPreFerences
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

    //recyclerview adapter
    private lateinit var mAdapter: MessageAdapter

    //Socket
    private val serverUri = "ws://10.80.161.156:8080/ws"
    private val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, serverUri)

    //RoomDao
//    private lateinit var db: Database





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        var name: String? = null
        val roomId = arguments?.getString("roomId")

        RetrofitClient.api.authGetUser(Authorization = SharedPreFerences(fragmentContext).dataBearerToken,
            email = SharedPreFerences(fragmentContext).dataUserMail!!
        ).enqueue(object : Callback<AuthUserGetResponse>{
            override fun onResponse(
                call: Call<AuthUserGetResponse>,
                response: Response<AuthUserGetResponse>
            ) {
                if(response.code() == 200){
                    SharedPreFerences(fragmentContext).dataUserName = response.body()?.name.toString()
                }
            }
            override fun onFailure(call: Call<AuthUserGetResponse>, t: Throwable) {
                Log.d("상태","${t.message}")
            }
        })


//        db = Room.databaseBuilder(
//            requireContext(),
//            Database::class.java, "database"
//        ).build()

//        val getMessage = db.messageDao().getAllMessage()

        val headerList = arrayListOf<StompHeader>()
        headerList.add(StompHeader("Authorization", SharedPreFerences(fragmentContext).dataBearerToken))//Socket header추가
        stompClient.connect(headerList) //소켓 연결
        name = SharedPreFerences(fragmentContext).dataUserName
        Log.d("상태","$name")
        val data = JSONObject()
        data.put("type","ENTER")
        data.put("roomId",roomId)
        data.put("sender",name)
        data.put("message","")

        stompClient.send("/pub/chat/send",data.toString()).subscribe()

        binding.messageButton.setOnClickListener {
            if(!binding.messageText.text.isNullOrEmpty()){
                SocketStompMessage(roomId!!,binding.messageText.text.toString(),name!!)
                binding.messageText.text = null
            }
        }
        binding.recyclerview.layoutManager = LinearLayoutManager(activity)
        mAdapter = MessageAdapter(fragmentContext)
        binding.recyclerview.adapter = mAdapter

        stompClient.topic("/sub/chat/user/"+"cksgur0612@dgsw.hs.kr").subscribe{
            val stompMessage = it
            val payload = stompMessage.payload
            val gson = Gson()
            val messageData = gson.fromJson(payload, MessageData::class.java)//message 받는거

            mAdapter.addData(Message(messageData.message,messageData.sender,messageData.type,messageData.time))
            activity?.runOnUiThread{
                mAdapter.notifyDataSetChanged()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val roomName = arguments?.getString("roomName")
        (activity as AppCompatActivity).supportActionBar?.apply {
//            setHomeAsUpIndicator(R.drawable.ic_arrow_back_black)
            //toolbar menu에 뒤로가기 icon
            setDisplayHomeAsUpEnabled(true)
            //toolbar title에 방이름띄우기
            title = "$roomName"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Log.d("상태","뒤로가기")
            }
        }

        when(item.itemId){
            R.drawable.baseline_arrow_back_ios_new_24 -> {
                //baseline_arrow_back_ios_new_24 icon을 클릭하면
                requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //fragment에서 context사용
        fragmentContext = context
    }



    override fun onDestroyView() {
        super.onDestroyView()
        stompClient.disconnect() //소켓 연결 해제
        mbinding = null
    }



    private fun SocketStompMessage(
        roomId: String,
        text: String,
        userName: String,
    ){

        stompClient.lifecycle().subscribe{
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

    }


}