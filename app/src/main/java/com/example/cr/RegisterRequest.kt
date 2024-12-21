package com.example.cr

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val telefono: String,
    val contrasena: String,
    val tipoUsuario: String,
    val biografia: String? = null,
    val specialties: List<String>? = null,
    val certifications: List<String>? = null,
    val studies: List<String>? = null
)