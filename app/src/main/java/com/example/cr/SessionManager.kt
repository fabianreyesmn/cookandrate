package com.example.cr

object SessionManager {
    private var currentUser: User? = null
    private var authToken: String? = null

    fun saveSession(user: User, token: String) {
        currentUser = user
        authToken = token
    }

    fun getCurrentUser(): User? = currentUser
    fun getAuthToken(): String? = authToken

    fun isLoggedIn(): Boolean = currentUser != null

    fun clearSession() {
        currentUser = null
        authToken = null
    }
}