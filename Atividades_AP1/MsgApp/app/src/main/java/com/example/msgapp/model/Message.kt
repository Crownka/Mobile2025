package com.example.msgapp.model

data class Message(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = 0L,
    val senderName: String = ""
)