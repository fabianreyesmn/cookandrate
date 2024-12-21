package com.example.cr

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProfesionalAdapter(
    private val chefList: List<ChefWithUserDetails>
) : RecyclerView.Adapter<ProfesionalAdapter.ChefViewHolder>() {

    class ChefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chefImage: ImageView = itemView.findViewById(R.id.chefImage)
        val chefName: TextView = itemView.findViewById(R.id.chefName)
        val chefRating: TextView = itemView.findViewById(R.id.chefRating)
        val chefType: TextView = itemView.findViewById(R.id.chefType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChefViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chef, parent, false)
        return ChefViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChefViewHolder, position: Int) {
        val chefWithUser = chefList[position]
        val chef = chefWithUser.chef
        val user = chefWithUser.user

        Log.d("ProfesionalAdapter", "User Name: ${user.Nombre}, Rating: ${chef.RatingPromedio}")

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load("http://192.168.0.10:3000/img/userIcons/" + user.imagen) // Ajusta la URL base según tu configuración
            .placeholder(R.drawable.cocinero)
            .circleCrop()
            .into(holder.chefImage)

        holder.chefName.text = "${user.Nombre} ${user.ApellidoP}"
        holder.chefRating.text = String.format("Rating: %.1f", chef.RatingPromedio)
        holder.chefType.text = "Profesional"
    }

    override fun getItemCount() = chefList.size
}