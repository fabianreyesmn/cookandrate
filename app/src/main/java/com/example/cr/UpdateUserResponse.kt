package com.example.cr

data class UpdateUserResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)