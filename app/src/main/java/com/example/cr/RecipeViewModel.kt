package com.example.cr

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val apiService = RetrofitClient.instance
    private val sessionManager = SessionManager

    private val _recipesList = MutableLiveData<List<Recipe>>()
    val recipesList: LiveData<List<Recipe>> = _recipesList

    fun fetchRecipes() {
        viewModelScope.launch {
            try {
                // Fetch recipes, chefs, and users concurrently
                val recipesResponse = async { apiService.getAllRecipes() }
                val chefsResponse = async { apiService.getAllChefs() }
                val usersResponse = async { apiService.getAllUsers() }
                val imagesResponse = async { apiService.listImages() }

                // Wait for all responses
                val recipes = recipesResponse.await().recetas
                val chefs = chefsResponse.await().chefs
                val users = usersResponse.await().users
                val images = imagesResponse.await().images

                // Create a map of User ID to Name and Image
                val userMap = users.associate {
                    it.ID_User to UserInfo(it.Nombre, it.imagen)
                }

                // Create a map of Chef ID to User Name and Image
                val chefNameAndImageMap = chefs.associate { chef ->
                    val userInfo = userMap[chef.ID_User]
                    chef.ID_Chef to UserInfo(
                        userInfo?.name ?: "Chef Desconocido",
                        userInfo?.image ?: ""
                    )
                }

                // Enrich recipes with chef names and images
                val enrichedRecipes = recipes.map { recipe ->
                    val chefInfo = chefNameAndImageMap[recipe.ID_Chef] ?: UserInfo("Chef Desconocido", "")

                    // Construct full image URL
                    val chefImageUrl = if (chefInfo.image.isNotBlank()) {
                        "http://192.168.0.10:3000/img/userIcons/${chefInfo.image}"
                    } else {
                        ""
                    }

                    // Map recipe images to full URLs
                    val recipeImageUrls = recipe.Imagenes.map { imageName ->
                        "http://192.168.0.10:3000/img/recetas/$imageName"
                    }

                    recipe.copy(
                        ChefNombre = chefInfo.name,
                        ChefImagen = chefImageUrl,
                        RecipeImageUrls = recipeImageUrls
                    )
                }

                _recipesList.value = enrichedRecipes
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipes, chefs, or users", e)
            }
        }
    }

    fun fetchRecipesForCurrentUser() {
        viewModelScope.launch {
            try {
                // Obtener el usuario actual
                val currentUser = sessionManager.getCurrentUser()

                if (currentUser != null) {
                    // Fetch recipes, chefs, and users concurrently
                    val recipesResponse = async { apiService.getAllRecipes() }
                    val chefsResponse = async { apiService.getAllChefs() }
                    val usersResponse = async { apiService.getAllUsers() }
                    val imagesResponse = async { apiService.listImages() }

                    // Wait for all responses
                    val recipes = recipesResponse.await().recetas
                    val chefs = chefsResponse.await().chefs
                    val users = usersResponse.await().users
                    val images = imagesResponse.await().images

                    // Create a map of User ID to Name and Image
                    val userMap = users.associate {
                        it.ID_User to UserInfo(it.Nombre, it.imagen)
                    }

                    // Create a map of Chef ID to User Name and Image
                    val chefNameAndImageMap = chefs.associate { chef ->
                        val userInfo = userMap[chef.ID_User]
                        chef.ID_Chef to UserInfo(
                            userInfo?.name ?: "Chef Desconocido",
                            userInfo?.image ?: ""
                        )
                    }

                    // Filtrar recetas del usuario actual
                    val userRecipes = recipes.filter { recipe ->
                        // Encuentra el chef de la receta
                        val chef = chefs.find { it.ID_Chef == recipe.ID_Chef }
                        // Compara el ID del chef con el ID del usuario actual
                        chef?.ID_User == currentUser.ID_User
                    }

                    // Enrich recipes with chef names and images
                    val enrichedRecipes = userRecipes.map { recipe ->
                        val chefInfo = chefNameAndImageMap[recipe.ID_Chef] ?: UserInfo("Chef Desconocido", "")

                        // Construct full image URL
                        val chefImageUrl = if (chefInfo.image.isNotBlank()) {
                            "http://192.168.0.8:3000/img/userIcons/${chefInfo.image}"
                        } else {
                            ""
                        }

                        // Map recipe images to full URLs
                        val recipeImageUrls = recipe.Imagenes.map { imageName ->
                            imageName
                        }

                        recipe.copy(
                            ChefNombre = chefInfo.name,
                            ChefImagen = chefImageUrl,
                            RecipeImageUrls = recipeImageUrls
                        )
                    }

                    _recipesList.value = enrichedRecipes
                } else {
                    // No hay usuario logueado
                    _recipesList.value = emptyList()
                    Log.e("RecipeViewModel", "No user logged in")
                }
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipes for current user", e)
                _recipesList.value = emptyList()
            }
        }
    }

    // Helper data class to store user info
    private data class UserInfo(val name: String, val image: String)
}