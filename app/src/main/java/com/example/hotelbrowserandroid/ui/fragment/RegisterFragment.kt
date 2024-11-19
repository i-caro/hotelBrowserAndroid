package com.example.hotelbrowserandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.model.UserEntity
import com.example.hotelbrowserandroid.databinding.FragmentRegisterBinding
import com.example.hotelbrowserandroid.ui.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.registerButton.setOnClickListener {
            val user = UserEntity(
                id = generateId(),
                name = binding.nameInput.text.toString(),
                surname = binding.surnameInput.text.toString(),
                email = binding.emailInput.text.toString(),
                phone = binding.phoneInput.text.toString(),
                password = binding.passwordInput.text.toString()
            )
            authViewModel.registerUser(user, {
                Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }, {
                Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show()
            })
        }

        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun generateId(): String {
        return (1..8).map { ('A'..'Z').random() }.joinToString("")
    }
}