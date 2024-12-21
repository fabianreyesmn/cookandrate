package com.example.cr

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    // Usuarios
    @GET("/read-users")
    suspend fun getAllUsers(): UserListResponse

    @GET("/login")
    suspend fun login(
        @Query("Email") email: String,
        @Query("password") password: String
    ): LoginResponse

    @POST("/register-user")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/update-user")
    suspend fun updateUser(
        @Body requestBody: RequestBody,
        @Part("imagen") imagen: MultipartBody.Part
    ): UpdateUserResponse

    @POST("/deactivate-account")
    suspend fun deactivateAccount(@Body userId: Map<String, String>): DeactivationResponse

    @POST("/checkEmail")
    suspend fun checkEmailAvailability(@Body email: Map<String, String>): EmailCheckResponse

    @GET("/find-user-data")
    suspend fun findUserByName(
        @Query("nombre") nombre: String,
        @Query("apellidoP") apellidoP: String,
        @Query("apellidoM") apellidoM: String
    ): UserDataResponse

    @POST("/find-user-by-id")
    suspend fun findUserById(@Body userId: Map<String, String>): UserProfileResponse

    @POST("/update-password")
    suspend fun updatePassword(@Body request: PasswordUpdateRequest): PasswordUpdateResponse

    // Chefs
    @GET("/read-chefs")
    suspend fun getAllChefs(): ChefsListResponse

    @POST("/find-chef-by-user-id")
    suspend fun findChefByUserId(@Body userId: Map<String, String>): ChefProfileResponse

    // Recetas
    @GET("/read-recetas")
    suspend fun getAllRecipes(): RecipesListResponse

    // Anuncios
    @GET("/read-anuncios")
    suspend fun getAllAds(): AdsListResponse

    // Archivos
    @GET("/list-files")
    suspend fun listFiles(): FileListResponse

    @GET("/img-list")
    suspend fun listImages(): ImageListResponse

    @GET
    suspend fun loadImage(@Url imageUrl: String): okhttp3.ResponseBody

    @GET("/ingredient-nutrition")
    suspend fun getIngredientNutrition(
        @Query("id_receta") recipeId: String
    ): List<IngredientNutritionResponse>

    @GET("/mapbox-token")
    suspend fun getMapboxToken(): MapboxTokenResponse

    @GET("/restaurant/{id}")
    suspend fun getRestaurantDetails(@Path("id") restaurantId: Int): RestaurantResponse
}