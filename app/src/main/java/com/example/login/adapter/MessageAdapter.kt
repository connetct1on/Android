package com.example.login.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.databinding.ItemRecyclerviewAddBinding
import com.example.login.databinding.ItemRecyclerviewChatMeBinding
import com.example.login.databinding.ItemRecyclerviewChatOtherBinding
import com.example.login.message.Message
import com.example.login.network.retrofit.request.SignupRequest
import com.example.login.network.sharedPreFerences.SharedPreFerences


class MessageAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = mutableListOf<Message>()

    //뷰 홀더 타입을 구분하기 위한 상수
    private val TYPE_ME_MESSAGE = 1
    private val TYPE_OTHER_MESSAGE = 2
    private val TYPE_ADD = 3

    fun addData(newData:Message){
        messages.addAll(listOf(newData))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TYPE_ME_MESSAGE){
            val binding = ItemRecyclerviewChatMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyMessageViewHolder(binding)
        } else if(viewType == TYPE_OTHER_MESSAGE){
            val binding = ItemRecyclerviewChatOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OtherMessageViewHolder(binding)
        } else{
            val binding = ItemRecyclerviewAddBinding.inflate(LayoutInflater.from(parent.context),parent, false)
            AddMessageViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        Log.d("상태","$message")

        val text = message.time
        val pattern = Regex("\\d{2}:\\d{2}")
        val time = pattern.find(text)?.value // 정규표현식에 매칭되는 문자열 추출
        val hour = time?.substring(0,2)?.toInt()

        if(holder.itemViewType == TYPE_OTHER_MESSAGE){
            val otherMessageViewHolder = holder as OtherMessageViewHolder
            otherMessageViewHolder.binding.textGchatMessageOther.text = message.message
            otherMessageViewHolder.binding.textGchatUserOther.text = message.sender
            if(time?.substring(0,2)?.toInt()!! < 12) {
                otherMessageViewHolder.binding.textGchatTimestampOther.text =
                    "오전 " + "$hour" + ":" + "${time.substring(3,5)}"
            } else {
                otherMessageViewHolder.binding.textGchatTimestampOther.text =
                    "오후 " + "${hour!! - 12}" + ":" + "${time.substring(3,5)}"
            }
        } else if(holder.itemViewType == TYPE_ME_MESSAGE){
            val myMessageViewHolder = holder as MyMessageViewHolder
            myMessageViewHolder.binding.textGchatMessageMe.text = message.message
            if(time?.substring(0,2)?.toInt()!! < 12) {
                myMessageViewHolder.binding.textGchatTimestampMe.text =
                    "오전 " + "$hour" + ":" + "${time.substring(3,5)}"
                Log.d("시간", "${time}")
            } else {
                myMessageViewHolder.binding.textGchatTimestampMe.text =
                    "오후 " + "${hour!! - 12}" + ":" + "${time.substring(3,5)}"
            }
        } else {
            Log.d("어댑터Add","${holder is AddMessageViewHolder} $holder")
            val addMessageViewHolder = holder as AddMessageViewHolder
            addMessageViewHolder.binding.text.text = "${message.sender}님이 입장하셨습니다."
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if(message.type == "ENTER"){
            TYPE_ADD
        } else if(message.type == "TALK" && message.sender == SharedPreFerences(context).dataUserName){
            TYPE_ME_MESSAGE
        } else{
            TYPE_OTHER_MESSAGE
        }
    }

    inner class MyMessageViewHolder(val binding: ItemRecyclerviewChatMeBinding):
            RecyclerView.ViewHolder(binding.root)

    inner class OtherMessageViewHolder(val binding: ItemRecyclerviewChatOtherBinding):
            RecyclerView.ViewHolder(binding.root)

    inner class AddMessageViewHolder(val binding: ItemRecyclerviewAddBinding):
            RecyclerView.ViewHolder(binding.root)
}