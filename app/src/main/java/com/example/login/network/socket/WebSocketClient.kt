package com.example.login.network.socket

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class WebSocketClient(serverUri: URI): WebSocketClient(serverUri){
    override fun onOpen(handshakedata: ServerHandshake?) {
        //WebSocket 연결이 열릴 때 호출된다.
        TODO("Not yet implemented")
    }

    override fun onMessage(message: String?) {
        //WebSocket 연결이 닫힐 때 호출된다.
        TODO("Not yet implemented")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        //WebSocket 연결에서 애러가 발생할 때 호출된다.
        TODO("Not yet implemented")
    }

    override fun onError(ex: Exception?) {
        //WebSocket 서버로부터 메시지를 수신할 때 호출된다.
        TODO("Not yet implemented")
    }

}