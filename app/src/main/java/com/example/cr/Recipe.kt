package com.example.cr

data class Recipe(
    val ID_Receta: String,
    val ID_Chef: String,
    val Titulo_Receta: String,
    val Ingredientes: Map<String, String>,
    val Pasos_Elaboracion: List<String>,
    val Imagenes: List<String> = emptyList(),
    val Comentarios: List<Comentario>? = null,
    val ChefNombre: String? = null,
    val ChefImagen: String? = null,
    val RecipeImageUrls: List<String> = emptyList()
)