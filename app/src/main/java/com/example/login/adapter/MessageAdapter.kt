package com.example.login.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.databinding.ItemRecyclerviewAddBinding
import com.example.login.databinding.ItemRecyclerviewChatMeBinding
import com.example.login.databinding.ItemRecyclerviewChatOtherBinding
import com.example.login.message.Message
import com.example.login.network.retrofit.request.SignupRequest


class MessageAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = mutableListOf<Message>()
    //뷰 홀더 타입을 구분하기 위한 상수
    private val TYPE_MESSAGE = 0
    private val TYPE_ADD = 1

    fun addData(newData:Message){
        messages.addAll(listOf(newData))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TYPE_MESSAGE){
            val binding = ItemRecyclerviewChatOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OtherMessageViewHolder(binding)
        } else {
            val binding = ItemRecyclerviewAddBinding.inflate(LayoutInflater.from(parent.context),parent, false)
            AddMessageViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        Log.d("상태","$message")

        if(holder.itemViewType == TYPE_MESSAGE){
            val time = message.time
            val otherMessageViewHolder = holder as OtherMessageViewHolder
            otherMessageViewHolder.binding.textGchatMessageOther.text = message.message
            otherMessageViewHolder.binding.textGchatUserOther.text = message.sender
            if(time.substring(8,9).toInt() < 12) {
                Log.d("시간", "${time.substring(8, 9)}")
                otherMessageViewHolder.binding.textGchatTimestampOther.text =
                    "오전 " + "${message.time.substring(11, 16)}"
            }
            else {
                Log.d("시간", "${time.substring(8, 9)}")
                otherMessageViewHolder.binding.textGchatTimestampOther.text =
                    "오후 " + "${message.time.substring(11, 16)}"
            }
        } else {
            val addMessageViewHolder = holder as AddMessageViewHolder
            addMessageViewHolder.binding.text.text = "${message.sender}님이 입장하셨습니다."
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if(message.type == "ENTER") TYPE_ADD else TYPE_MESSAGE
    }

    inner class MyMessageViewHolder(val binding: ItemRecyclerviewChatMeBinding):
            RecyclerView.ViewHolder(binding.root)

    inner class OtherMessageViewHolder(val binding: ItemRecyclerviewChatOtherBinding):
            RecyclerView.ViewHolder(binding.root)

    inner class AddMessageViewHolder(val binding: ItemRecyclerviewAddBinding):
            RecyclerView.ViewHolder(binding.root)
}