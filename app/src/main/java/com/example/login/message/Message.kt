package com.example.login.message

import java.util.Date

data class Message(
    val message: String,
    val sender: String,
    val type: String
//    val date: Date TODO 나중에는 메시지 시간도 할예정
)
