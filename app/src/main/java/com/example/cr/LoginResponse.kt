package com.example.cr

data class LoginResponse(
    val user: User,
    val message: String,
    val token: String
)