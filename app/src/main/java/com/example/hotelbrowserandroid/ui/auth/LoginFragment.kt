package com.example.hotelbrowserandroid.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.remote.repositories.BookingRepository
import com.example.hotelbrowserandroid.data.remote.repositories.ServiceRepository
import com.example.hotelbrowserandroid.data.remote.repositories.UserRepository
import com.example.hotelbrowserandroid.databinding.FragmentLoginBinding
import com.example.hotelbrowserandroid.ui.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class User(val id: Int, val name: String, val email: String, val password: String)

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var usersRepository: UserRepository
    private lateinit var bookingRepository: BookingRepository
    private lateinit var serviceRepository: ServiceRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                val isSuccess = authViewModel.login(email, password)
                if (isSuccess) {
                    val userLogged = usersRepository.getUserByEmail(email).first()!!
                    val sharedPreferences =
                        requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("logged_in_user_email", email).apply()
                    sharedPreferences.edit().putInt("logged_in_user_id", userLogged.id).apply()


                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_profile)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Invalid email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.registerText.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
    }
}