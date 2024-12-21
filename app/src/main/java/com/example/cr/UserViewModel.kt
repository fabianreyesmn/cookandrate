package com.example.cr

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel : ViewModel() {
    private val apiService = RetrofitClient.instance

    // Cambia a un LiveData que contenga el usuario logueado
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    // Token de autenticación
    private val _authToken = MutableLiveData<String?>()
    val authToken: LiveData<String?> = _authToken

    init {
        // Cargar usuario actual del Singleton al iniciar
        _currentUser.value = SessionManager.getCurrentUser()
        _authToken.value = SessionManager.getAuthToken()
    }

    // New LiveData for image list
    private val _imageList = MutableLiveData<List<String>>()
    val imageList: LiveData<List<String>> = _imageList

    // Method to fetch image list
    fun fetchImageList() {
        viewModelScope.launch {
            try {
                val response = apiService.listImages()
                _imageList.value = response.images
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error fetching image list", e)
                _imageList.value = emptyList()
            }
        }
    }

    // Method to get full image URL for the current user
    fun getCurrentUserImageUrl(): String? {
        val currentUser = _currentUser.value

        if (currentUser != null) {
            // Construct full URL directly using the user's image
            return "http://192.168.0.8:3000/img/userIcons/${currentUser.imagen}"
        }
        return null
    }

    fun isUserLoggedIn(): Boolean = SessionManager.isLoggedIn()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.login(email, password)

                // Guardar sesión en el Singleton
                SessionManager.saveSession(response.user, response.token)

                // Actualizar LiveData
                _currentUser.value = response.user
                _authToken.value = response.token

            } catch (e: Exception) {
                Log.e("UserViewModel", "Login error", e)
                _currentUser.value = null
                _authToken.value = null
            }
        }
    }

    fun logout() {
        // Limpiar sesión en el Singleton
        SessionManager.clearSession()

        // Actualizar LiveData
        _currentUser.value = null
        _authToken.value = null
    }

    // Sealed class para manejar el resultado del registro
    sealed class RegistrationResult {
        data class Success(val user: User) : RegistrationResult()
        data class Error(val message: String) : RegistrationResult()
    }

    // LiveData para el resultado del registro
    private val _registrationResult = MutableLiveData<RegistrationResult?>()
    val registrationResult: LiveData<RegistrationResult?> = _registrationResult

    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = apiService.registerUser(registerRequest)
                _registrationResult.value = RegistrationResult.Success(response.user)
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is HttpException -> {
                        // Manejar errores específicos de HTTP
                        when (e.code()) {
                            400 -> "Error en los datos de registro"
                            409 -> "El email ya está registrado"
                            else -> "Error de registro: ${e.message()}"
                        }
                    }
                    else -> "Error de conexión: ${e.message}"
                }
                _registrationResult.value = RegistrationResult.Error(errorMessage)
            }
        }
    }
}