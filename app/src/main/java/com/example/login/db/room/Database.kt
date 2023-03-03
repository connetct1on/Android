package com.example.login.db.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomMessage::class], version = 1)
abstract class Database: RoomDatabase(){
    abstract fun messageDao(): RoomMessageDao
}