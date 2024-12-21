package com.example.cr

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {
    /*private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResult.observe(viewLifecycleOwner) { response ->
            when (response.message) {
                "ACC" -> {
                    // Inicio de sesión exitoso
                    findNavController().navigate(R.id.nav)
                }
                "COI" -> {
                    // Contraseña incorrecta
                    showError("Contraseña incorrecta")
                }
                "UNE" -> {
                    // Usuario no encontrado
                    showError("Usuario no encontrado")
                }
            }
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.login(email, password)
        }
    }*/
}