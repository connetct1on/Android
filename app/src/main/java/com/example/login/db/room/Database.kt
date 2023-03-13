package com.example.login.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login.db.room.dao.MessageDao
import com.example.login.db.room.dao.RoomDao

@Database(entities = [RoomMessage::class, RoomRoom::class], version = 1)
abstract class Database: RoomDatabase(){
    abstract fun messageDao(): MessageDao
    abstract fun roomDao(): RoomDao

}