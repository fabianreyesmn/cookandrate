package com.example.cr

data class NutrientesMainResponse(
    val calcio: Double = 0.0,
    val hierro: Double = 0.0,
    val vitaminas: List<VitaminResponse> = emptyList()
)