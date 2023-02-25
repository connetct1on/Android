package com.example.login.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.databinding.ItemRecyclerviewAddBinding
import com.example.login.databinding.ItemRecyclerviewChatMeBinding
import com.example.login.databinding.ItemRecyclerviewChatOtherBinding
import com.example.login.message.Message
import com.example.login.network.retrofit.request.SignupRequest


class MessageAdapter(private val messages: List<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //뷰 홀더 타입을 구분하기 위한 상수
    private val TYPE_MY_MESSAGE = 0
    private val TYPE_OTHER_MESSAGE = 1
    private val TYPE_ADD = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TYPE_MY_MESSAGE){
            val binding = ItemRecyclerviewChatMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyMessageViewHolder(binding)
        } else if(viewType == TYPE_OTHER_MESSAGE){
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

        if(holder.itemViewType == TYPE_MY_MESSAGE){
            val myMessageViewHolder = holder as MyMessageViewHolder
            myMessageViewHolder.binding.textGchatMessageMe.text = message.message
        } else if(holder.itemViewType == TYPE_OTHER_MESSAGE){
            val otherMessageViewHolder = holder as OtherMessageViewHolder
            otherMessageViewHolder.binding.textGchatMessageOther.text = message.message
        } else {
            val addMessageViewHolder = holder as AddMessageViewHolder
            addMessageViewHolder.binding.text.text = "${message.sender}님이 입장하셨습니다."
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]

        return message.senderInt
    }

    inner class MyMessageViewHolder(val binding: ItemRecyclerviewChatMeBinding):
            RecyclerView.ViewHolder(binding.root)

    inner class OtherMessageViewHolder(val binding: ItemRecyclerviewChatOtherBinding):
            RecyclerView.ViewHolder(binding.root)

    inner class AddMessageViewHolder(val binding: ItemRecyclerviewAddBinding):
            RecyclerView.ViewHolder(binding.root)
}