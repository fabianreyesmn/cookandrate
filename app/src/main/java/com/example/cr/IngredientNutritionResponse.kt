package com.example.cr

data class IngredientNutritionResponse(
    val nombre: String,
    val calorias: Double = 0.0,
    val proteinas: Double = 0.0,
    val carbohidratos: Double = 0.0,
    val grasas: Double = 0.0,
    val nutrientesPrincipales: NutrientesMainResponse = NutrientesMainResponse(),
    val error: String? = null
)