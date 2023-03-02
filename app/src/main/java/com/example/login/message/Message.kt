package com.example.login.message


import io.realm.RealmObject
import java.util.Date

data class Message(
    val message: String,
    val sender: String,
    val type: String,
    val time: String
) : RealmObject()

