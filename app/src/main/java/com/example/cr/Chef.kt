package com.example.cr

data class Chef(
    val ID_Chef: String,
    val ID_User: String,
    val TipoChef: String,
    val Nombre: String, // Added for chef name
    val imagen: String, // Added for chef image
    val Especialidades: List<String>?,
    val Certificaciones: List<String>?,
    val RatingPromedio: Double,
    val Seguidores: List<Any>
)