package com.example.login.db.room

import androidx.room.Entity

@Entity("room")
data class RoomRoom(
    val access: Int,
    val idx: Int,
    val name: String,
    val roomId: String
)