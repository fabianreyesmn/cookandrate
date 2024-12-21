package com.example.cr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        // Observa cambios en el usuario actual
        viewModel.currentUser.observe(this) { user ->
            user?.let {
                // Usuario logueado exitosamente
                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("USER_ID", it.ID_User).apply()
                val intent = Intent(this, Feed::class.java)
                startActivity(intent)
                finish() // Cierra la actividad de login
            }
        }

        btnEntrar.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            when {
                email.isEmpty() -> {
                    emailEditText.error = "Email cannot be empty"
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    passwordEditText.error = "Password cannot be empty"
                    return@setOnClickListener
                }
                else -> viewModel.login(email, password)
            }
        }
    }
}