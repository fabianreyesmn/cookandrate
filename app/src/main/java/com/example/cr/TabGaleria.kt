package com.example.cr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TabGaleria : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noImagesTextView: TextView
    private lateinit var galleryAdapter: GalleryAdapter

    private val recipeViewModel: RecipeViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tab_galeria, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewGallery)
        noImagesTextView = view.findViewById(R.id.textViewNoImages)

        // Setup RecyclerView with 2 columns
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Initialize adapter with empty list
        galleryAdapter = GalleryAdapter(emptyList())
        recyclerView.adapter = galleryAdapter

        // Observe recipes for the current user
        recipeViewModel.recipesList.observe(viewLifecycleOwner) { recipes ->
            // Collect all recipe image URLs
            val allRecipeImageUrls = recipes.flatMap { it.RecipeImageUrls }

            // Update adapter and visibility
            if (allRecipeImageUrls.isNotEmpty()) {
                galleryAdapter.updateImages(allRecipeImageUrls)
                recyclerView.visibility = View.VISIBLE
                noImagesTextView.visibility = View.GONE
            } else {
                recyclerView.visibility = View.GONE
                noImagesTextView.visibility = View.VISIBLE
            }
        }

        // Fetch recipes for current user
        recipeViewModel.fetchRecipesForCurrentUser()

        return view
    }
}