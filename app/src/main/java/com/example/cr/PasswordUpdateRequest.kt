package com.example.cr

data class PasswordUpdateRequest(
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val newPassword: String
)