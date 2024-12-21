package com.example.cr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NutritionalInfoAdapter(
    private val ingredientNutrition: List<IngredientNutritionResponse>
) : RecyclerView.Adapter<NutritionalInfoAdapter.NutritionalInfoViewHolder>() {

    class NutritionalInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientNameTextView: TextView = view.findViewById(R.id.ingredientNameTextView)
        val caloriesTextView: TextView = view.findViewById(R.id.caloriesTextView)
        val proteinsTextView: TextView = view.findViewById(R.id.proteinsTextView)
        val carbsTextView: TextView = view.findViewById(R.id.carbsTextView)
        val fatsTextView: TextView = view.findViewById(R.id.fatsTextView)
        val vitaminsTextView: TextView = view.findViewById(R.id.vitaminsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionalInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.nutritional_info_item, parent, false)
        return NutritionalInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: NutritionalInfoViewHolder, position: Int) {
        val ingredient = ingredientNutrition[position]

        holder.ingredientNameTextView.text = ingredient.nombre
        holder.caloriesTextView.text = "Calorías: ${ingredient.calorias}"
        holder.proteinsTextView.text = "Proteínas: ${ingredient.proteinas}g"
        holder.carbsTextView.text = "Carbohidratos: ${ingredient.carbohidratos}g"
        holder.fatsTextView.text = "Grasas: ${ingredient.grasas}g"

        // Format vitamins
        val vitaminsList = ingredient.nutrientesPrincipales.vitaminas
            .joinToString(", ") { "${it.nombre}: ${it.cantidad}" }
        holder.vitaminsTextView.text = "Vitaminas: $vitaminsList"
    }

    override fun getItemCount() = ingredientNutrition.size
}