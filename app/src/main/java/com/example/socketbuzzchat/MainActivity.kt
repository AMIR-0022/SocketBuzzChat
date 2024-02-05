package com.example.socketbuzzchat

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class MainActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView;
    private val webSocket = WebSocketClient(this);

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus);
        val btnConnect: AppCompatButton = findViewById(R.id.btnConnect);
        val btnSendMessage: AppCompatButton = findViewById(R.id.btnSendMessage);
        val btnDisconnect: AppCompatButton = findViewById(R.id.btnDisconnect);


        btnConnect.setOnClickListener {
            webSocket.connect();
        }

        btnSendMessage.setOnClickListener {
            val message = "Hey Dev"
            if (webSocket.sendMessage(message)) {
                Toast.makeText(this, "Message sent: $message", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
            }
        }

        btnDisconnect.setOnClickListener {
            webSocket.disconnect();
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket.disconnect()
    }

    fun updateStatus(status: String) {
        runOnUiThread {
            tvStatus.text = status
        }
    }

}



//private var socket: Socket? = null

//socket!!.on(Socket.EVENT_CONNECT) { args: Array<Any?>? ->
//    Toast.makeText(this, "Connected to server", Toast.LENGTH_LONG).show()
//    tvStatus.text = "Connected to Server"
//}
//socket!!.on(Socket.EVENT_DISCONNECT) { args: Array<Any?>? ->
//    Toast.makeText(this, "Disconnected from server", Toast.LENGTH_LONG).show()
//    tvStatus.text = "Disconnected"
//}

//private fun connectToSocket() {
//    try {
//        val options = IO.Options()
//        options.query = ""
//        socket = IO.socket("")
//        socket!!.connect()
//
//    } catch (e: URISyntaxException) {
//        e.printStackTrace()
//    }
//}
//
//private fun disconnectFromSocket() {
//    if (socket?.connected() == true) {
//        socket?.disconnect()
//    }
//}