package com.example.login.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.login.db.room.RoomRoom

@Dao
interface RoomDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(roomRoom: RoomRoom)

    @Query("SELECT * FROM ")
    fun getAllRoom()
}