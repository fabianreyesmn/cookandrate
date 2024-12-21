package com.example.cr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SitesFragment : Fragment() {
    private lateinit var restaurantsRecyclerView: RecyclerView

    // Lista de restaurantes hardcodeada (sustituir con llamada a API)
    private val restaurantsList = listOf(
        Restaurant(1, "Restaurante 1", 41.387, 2.153),
        Restaurant(2, "Restaurante 2", 19.4326, -99.1949),
        Restaurant(3, "Restaurante 3", 48.8490, 2.3759),
        Restaurant(4, "Restaurante 4", 40.7444, -73.9828),
        Restaurant(5, "Restaurante 5", 13.7409, 100.5676)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sites, container, false)

        // Configurar RecyclerView
        restaurantsRecyclerView = view.findViewById(R.id.restaurantsRecyclerView)
        restaurantsRecyclerView.layoutManager = LinearLayoutManager(context)

        // Crear adaptador
        val adapter = RestaurantAdapter(restaurantsList) { restaurant ->
            // Manejar clic en restaurante
            Toast.makeText(context, "Seleccionado: ${restaurant.name}", Toast.LENGTH_SHORT).show()
        }

        restaurantsRecyclerView.adapter = adapter

        return view
    }
}