package com.example.cr

import RecipeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var apiService: ApiService
    private lateinit var ctrlPublicacion: CtrlPublicacion

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout de feed
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView para recetas
        val recipeRecyclerView: RecyclerView = view.findViewById(R.id.recipeRecyclerView)
        recipeAdapter = RecipeAdapter()
        recipeRecyclerView.adapter = recipeAdapter
        recipeRecyclerView.layoutManager = LinearLayoutManager(context)

        // Inicializar ViewModel
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        // Observar cambios en la lista de recetas
        recipeViewModel.recipesList.observe(viewLifecycleOwner) { recipes ->
            // Invertir la lista para mostrar de última a primera
            recipeAdapter.submitList(recipes.reversed())
        }

        // Cargar recetas
        recipeViewModel.fetchRecipes()
    }
}