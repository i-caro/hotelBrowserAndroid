package com.example.hotelbrowserandroid.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.databinding.FragmentLoginBinding
import com.example.hotelbrowserandroid.ui.auth.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                val userLogged = authViewModel.getUserByEmail(email)
                val userId = userLogged!!.id!!
                val isSuccess = authViewModel.login(email, password)
                if (isSuccess) {
                    val userLogged = authViewModel.getUserByEmail(email)

                    if (userLogged?.id != null) {
                        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("logged_in_user_email", userLogged.email)
                            putInt("logged_in_user_id", userId)
                            Log.d("user", "Se ha guardado el id $userId")
                            apply()
                        }

                        Toast.makeText(requireContext(), "Login exitoso", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_profile)
                    } else {
                        Toast.makeText(requireContext(), "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
                        Log.e("LoginFragment", "El usuario es nulo o no tiene ID.")
                    }
                } else {
                    Toast.makeText(requireContext(), "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.registerText.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
    }
}