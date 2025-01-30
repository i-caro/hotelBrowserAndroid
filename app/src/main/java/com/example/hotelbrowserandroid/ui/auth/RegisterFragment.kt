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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val name = binding.nameInput.text.toString().trim()
            val surname = binding.surnameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val phone = binding.phoneInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (name.isEmpty()) {
                showToast("Name cannot be empty")
                return@setOnClickListener
            }

            if (surname.isEmpty()) {
                showToast("Surname cannot be empty")
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Invalid email format")
                return@setOnClickListener
            }

            if (phone.isEmpty() || !phone.matches(Regex("^\\+?[0-9]{10,15}\$"))) {
                showToast("Invalid phone number format. Use + and 10-15 digits.")
                return@setOnClickListener
            }

            if (password.length < 6) {
                showToast("Password must be at least 6 characters")
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                val isRegistered = authViewModel.isEmailRegistered(email)

                withContext(Dispatchers.Main) {
                    if (isRegistered) {
                        showToast("Email is already registered")
                    } else {
                        val isSuccess = authViewModel.register(name, email, password, surname, phone)
                        if (isSuccess) {
                            showToast("Registration successful")
                            findNavController().navigate(R.id.action_register_to_login)
                        } else {
                            showToast("Registration failed")
                        }
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
