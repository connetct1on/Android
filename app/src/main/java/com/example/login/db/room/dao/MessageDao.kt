package com.example.login.db.room.dao

import androidx.room.*
import com.example.login.db.room.RoomMessage

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //혹시나 중복된다면 새로운 방을 만들어서 만듬 insert할때
    suspend fun insert(message: RoomMessage)

    @Query("SELECT * FROM Message")
    fun getAllMessage()

    @Delete
    fun delete()
}