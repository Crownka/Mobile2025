package com.example.msgapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.msgapp.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class MsgViewModel(application: Application) : AndroidViewModel(application) {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _currentRoom = MutableStateFlow<String?>(null)
    val currentRoom: StateFlow<String?> = _currentRoom

    private var messageListener: ValueEventListener? = null
    private val database = Firebase.database.reference

    fun joinRoom(roomId: String) {
        if (roomId.isBlank()) return
        leaveRoom()
        _currentRoom.value = roomId
        messageListener = createMessageListener()
        database.child("rooms").child(roomId).child("messages")
            .orderByChild("timestamp")
            .addValueEventListener(messageListener!!)
    }

    fun leaveRoom() {
        messageListener?.let {
            _currentRoom.value?.let { room ->
                database.child("rooms").child(room).child("messages").removeEventListener(it)
            }
        }
        _messages.value = emptyList()
        _currentRoom.value = null
    }

    private fun createMessageListener(): ValueEventListener {
        return object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val msgs = snapshot.children.mapNotNull {
                    it.getValue(Message::class.java)
                }
                _messages.value = msgs
            }

            override fun onCancelled(error: DatabaseError) {}
        }
    }

    fun sendMessage(senderId: String, senderName: String, text: String) {
        val currentRoomId = _currentRoom.value ?: return
        if (text.isBlank()) return

        val ref = database.child("rooms").child(currentRoomId).child("messages")
        val key = ref.push().key ?: UUID.randomUUID().toString()
        val msg = Message(
            id = key,
            senderId = senderId,
            senderName = senderName,
            text = text,
            timestamp = System.currentTimeMillis()
        )
        ref.child(key).setValue(msg)
    }

    override fun onCleared() {
        super.onCleared()
        leaveRoom()
    }
}