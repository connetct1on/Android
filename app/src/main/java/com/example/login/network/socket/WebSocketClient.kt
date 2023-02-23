package com.example.login.network.socket

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.lang.Exception
import java.net.URI

class WebSocketClient(serverUri: URI): WebSocketClient(serverUri){
    override fun onOpen(handshakedata: ServerHandshake?) {
        //WebSocket 연결이 열릴 때 호출된다.
        Log.d("상태","연결")
        val jsonObject = JSONObject()
        jsonObject.put("type","ENTER")
        jsonObject.put("roomId","b12f59f1-10fe-4880-947b-8639ba3812e6")
        jsonObject.put("sender","HamTory")
        jsonObject.put("message","Hello")

        val message = jsonObject.toString()
        send(message)
    }

    override fun onMessage(message: String?) {
        //WebSocket 서버로부터 메시지를 수신할 때 호출된다.
        Log.d("상태","message: ${message.toString()}")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        //WebSocket 연결이 닫힐 때 호출된다.
        Log.d("상태","연결해제: code: $code, reason: $reason, remote: $remote")
    }

    override fun onError(ex: Exception?) {
        //WebSocket 연결에서 애러가 발생하면 호출된다.
        Log.d("상태","연결애러: ${ex?.message}")
    }
}