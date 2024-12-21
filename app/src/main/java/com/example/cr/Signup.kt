package com.example.cr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Signup : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var userTypeSpinner: Spinner

    // Campos de registro
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoPEditText: EditText
    private lateinit var apellidoMEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var telefonoEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    // Campos adicionales según tipo de usuario
    private lateinit var specialtyEditText: EditText
    private lateinit var certificationEditText: EditText
    private lateinit var studiesEditText: EditText
    private lateinit var biografiaEditText: EditText

    // Tipo de usuario
    private lateinit var tipoUsuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)

        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Inicializar todos los EditText
        nombreEditText = findViewById(R.id.nombreEditText)
        //apellidoPEditText = findViewById(R.id.apellidoPEditText)
        //apellidoMEditText = findViewById(R.id.apellidoMEditText)
        emailEditText = findViewById(R.id.emailEditText)
        telefonoEditText = findViewById(R.id.telefonoEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        biografiaEditText = findViewById(R.id.biografiaEditText)

        // Campos ya existentes de tu implementación anterior
        userTypeSpinner = findViewById(R.id.userTypeSpinner)
        specialtyEditText = findViewById(R.id.specialtyEditText)
        certificationEditText = findViewById(R.id.certificationEditText)
        studiesEditText = findViewById(R.id.studiesEditText)

        // Configurar el spinner (código de tu implementación anterior)
        setupUserTypeSpinner()

        // Botón de registro
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener {
            if (validateInput()) {
                registerUser()
            }
        }

        // Observar resultado del registro
        viewModel.registrationResult.observe(this) { response ->
            when (response) {
                is UserViewModel.RegistrationResult.Success -> {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is UserViewModel.RegistrationResult.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                null -> {} // Estado inicial o reseteo
            }
        }
    }

    private fun setupUserTypeSpinner() {
        val userTypes = arrayOf(
            "Seleccionar tipo de usuario",
            "Chef Profesional",
            "Chef Aficionado",
            "Crítico",
            "Consumidor General"
        )
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            userTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userTypeSpinner.adapter = adapter

        userTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Mostrar/ocultar campos según el tipo de usuario
                when (userTypes[position]) {
                    "Chef Profesional" -> {
                        specialtyEditText.visibility = View.VISIBLE
                        certificationEditText.visibility = View.VISIBLE
                        studiesEditText.visibility = View.GONE
                        biografiaEditText.visibility = View.VISIBLE
                    }
                    "Chef Aficionado" -> {
                        specialtyEditText.visibility = View.VISIBLE
                        certificationEditText.visibility = View.GONE
                        studiesEditText.visibility = View.GONE
                        biografiaEditText.visibility = View.VISIBLE
                    }
                    "Crítico" -> {
                        specialtyEditText.visibility = View.GONE
                        certificationEditText.visibility = View.GONE
                        studiesEditText.visibility = View.VISIBLE
                        biografiaEditText.visibility = View.VISIBLE
                    }
                    "Consumidor General" -> {
                        specialtyEditText.visibility = View.GONE
                        certificationEditText.visibility = View.GONE
                        studiesEditText.visibility = View.GONE
                        biografiaEditText.visibility = View.VISIBLE
                    }
                    else -> {
                        specialtyEditText.visibility = View.GONE
                        certificationEditText.visibility = View.GONE
                        studiesEditText.visibility = View.GONE
                        biografiaEditText.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ocultar todos los campos adicionales
                specialtyEditText.visibility = View.GONE
                certificationEditText.visibility = View.GONE
                studiesEditText.visibility = View.GONE
                biografiaEditText.visibility = View.GONE
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        // Validar campos obligatorios
        if (nombreEditText.text.toString().trim().isEmpty()) {
            nombreEditText.error = "Nombre es requerido"
            isValid = false
        }

        if (emailEditText.text.toString().trim().isEmpty()) {
            emailEditText.error = "Email es requerido"
            isValid = false
        }

        if (telefonoEditText.text.toString().trim().isEmpty()) {
            telefonoEditText.error = "Teléfono es requerido"
            isValid = false
        }

        // Validar contraseña
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        when {
            password.isEmpty() -> {
                passwordEditText.error = "Contraseña es requerida"
                isValid = false
            }
            password.length < 6 -> {
                passwordEditText.error = "La contraseña debe tener al menos 6 caracteres"
                isValid = false
            }
            password != confirmPassword -> {
                confirmPasswordEditText.error = "Las contraseñas no coinciden"
                isValid = false
            }
        }

        // Validar tipo de usuario seleccionado
        if (userTypeSpinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Seleccione un tipo de usuario", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun registerUser() {
        // Preparar datos para el registro
        val specialties = when (userTypeSpinner.selectedItem.toString()) {
            "Chef Profesional", "Chef Aficionado" ->
                specialtyEditText.text.toString().split(",").map { it.trim() }
            else -> null
        }

        val certifications = when (userTypeSpinner.selectedItem.toString()) {
            "Chef Profesional" ->
                certificationEditText.text.toString().split(",").map { it.trim() }
            else -> null
        }

        val studies = when (userTypeSpinner.selectedItem.toString()) {
            "Crítico" ->
                studiesEditText.text.toString().split(",").map { it.trim() }
            else -> null
        }

        if(userTypeSpinner.selectedItem.toString() == "Chef Profesional"){
            tipoUsuario = "chefPro"
        }
        if(userTypeSpinner.selectedItem.toString() == "Chef Aficionado"){
            tipoUsuario = "chefAf"
        }
        if(userTypeSpinner.selectedItem.toString() == "Crítico"){
            tipoUsuario = "critico"
        }

        val registerRequest = RegisterRequest(
            nombre = nombreEditText.text.toString().trim(),
            email = emailEditText.text.toString().trim(),
            telefono = telefonoEditText.text.toString().trim(),
            contrasena = passwordEditText.text.toString().trim(),
            tipoUsuario = tipoUsuario,
            biografia = biografiaEditText.text.toString().trim().takeIf { it.isNotEmpty() },
            specialties = specialties,
            certifications = certifications,
            studies = studies
        )

        // Llamar al método de registro en el ViewModel
        viewModel.registerUser(registerRequest)
    }
}