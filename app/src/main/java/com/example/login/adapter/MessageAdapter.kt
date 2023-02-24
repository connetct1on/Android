package com.example.login.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.databinding.ItemRecyclerviewChatMeBinding
import com.example.login.databinding.ItemRecyclerviewChatOtherBinding
import com.example.login.message.Message
import com.example.login.network.retrofit.request.SignupRequest


class MessageAdapter(private val messages: List<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //뷰 홀더 타입을 구분하기 위한 상수
    private val TYPE_MY_MESSAGE = 0
    private val TYPE_OTHER_MESSAGE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TYPE_MY_MESSAGE){
            val binding = ItemRecyclerviewChatMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyMessageViewHolder(binding)
        } else {
            val binding = ItemRecyclerviewChatOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OtherMessageViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        if(holder.itemViewType == TYPE_MY_MESSAGE){
            val myMessageViewHolder = holder as MyMessageViewHolder
            myMessageViewHolder.binding.textGchatMessageMe.text = message.message
        } else {
            val otherMessageViewHolder = holder as OtherMessageViewHolder
            otherMessageViewHolder.binding.textGchatMessageOther.text = message.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]

        return if(message.sender) TYPE_MY_MESSAGE else TYPE_OTHER_MESSAGE
    }

    inner class MyMessageViewHolder(val binding: ItemRecyclerviewChatMeBinding):
            RecyclerView.ViewHolder(binding.root)

    inner class OtherMessageViewHolder(val binding: ItemRecyclerviewChatOtherBinding):
            RecyclerView.ViewHolder(binding.root)

}