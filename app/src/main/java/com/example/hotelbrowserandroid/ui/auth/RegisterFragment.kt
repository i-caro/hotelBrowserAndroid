package com.example.hotelbrowserandroid.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.databinding.FragmentRegisterBinding
import com.example.hotelbrowserandroid.ui.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val surname = binding.surnameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val phone = binding.phoneInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (name.isBlank()) {
                Toast.makeText(requireContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (surname.isBlank()) {
                Toast.makeText(requireContext(), "Surname cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phone.isBlank() || !phone.matches(Regex("^\\+?[0-9]{10,15}\$"))) {
                Toast.makeText(requireContext(), "Invalid phone number format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(requireContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                if (authViewModel.isEmailRegistered(email)) {
                    Toast.makeText(requireContext(), "Email is already registered", Toast.LENGTH_SHORT).show()
                } else {
                    val isSuccess = authViewModel.register(name, surname, email, phone, password)
                    if (isSuccess) {
                        Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_register_to_login)
                    } else {
                        Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
    }
}