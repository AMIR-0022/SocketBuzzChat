package com.example.socketbuzzchat

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONException
import org.json.JSONObject

class WebSocketClient (private val mainActivity: MainActivity){

    private val url: String = "wss://buzzmehi.com/socketChat/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFzaHdpbkBhZG1pbi5jb20iLCJpYXQiOjE3MDcyMDA2MzEsImV4cCI6MTcwNzIyOTQzMX0.843xcAMbZr0u_URly_zUlOKIDdX9zNdCQWA1pHLQwEY";
    private val authToken: String = "token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im11ekBhZG1pbi5jb20iLCJpYXQiOjE3MDcxMTg1MzMsImV4cCI6MTcwNzE0NzMzM30.5C_rPRI8w_MVrzRRLvHLVaaae_84vCxXYl0fHp7OAnU";

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect() {
        val request = Request.Builder()
            .url(url)
            //.addHeader("Authorization", "Bearer $authToken")
            .build()
        webSocket = client.newWebSocket(request, MyWebSocketListener())
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnect")
    }

    fun sendMessage(message: String): Boolean {
        return webSocket?.send(message) == true
    }

    inner class MyWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Log.d("WebSocket", "WebSocket connection opened")

            Log.d("WebSocket", "Response: $response")
            Log.d("WebSocket", "Request: "+response.request.toString())
            Log.d("WebSocket", "Headers: "+response.headers.toString())
            Log.d("WebSocket", "Message: "+response.message.toString())

            mainActivity.updateStatus("Connected to Server")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.d("WebSocket", "Received message: $text")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("WebSocket", "WebSocket connection closed. Code: $code, Reason: $reason")
            mainActivity.updateStatus("Disconnected from Server")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("WebSocket", "WebSocket connection failure", t)
        }
    }


}