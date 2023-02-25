package com.example.login.message

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json


data class MessageData(
    val message: String,
    val roomId: String,
    val sender: String,
    val type: String
)