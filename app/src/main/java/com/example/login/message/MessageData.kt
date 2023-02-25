package com.example.login.message

data class MessageData(
    val message: String,
    val roomId: String,
    val sender: String,
    val type: String
)