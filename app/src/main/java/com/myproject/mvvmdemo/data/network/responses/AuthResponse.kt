package com.myproject.mvvmdemo.data.network.responses

import com.myproject.mvvmdemo.data.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)

// this is data class of json response which is directly parse our json to kotlin objects