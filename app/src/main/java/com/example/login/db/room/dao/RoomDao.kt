package com.example.login.db.room.dao

import androidx.room.*
import com.example.login.db.room.RoomRoom
import com.example.login.network.retrofit.response.FindRoomResponse

@Dao
interface RoomDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(roomRoom: FindRoomResponse)

    @Query("SELECT * FROM room WHERE roomId") // WHERE roomId = :roomId
    fun getAllRoom()

    @Delete
    fun delete()
}