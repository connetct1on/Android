package com.example.login.db.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Message")
data class RoomMessage (
    @PrimaryKey
    val roomId: String,
    val message: String,
    val sender: String,
    val type: String,
    val time: Date
)