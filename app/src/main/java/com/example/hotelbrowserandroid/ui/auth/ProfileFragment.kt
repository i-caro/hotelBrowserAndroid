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
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.databinding.FragmentProfileBinding
import com.example.hotelbrowserandroid.ui.auth.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar los listeners de los botones
        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.logoutButton.setOnClickListener {
            logoutUser()
        }

        fetchUserData()
    }

    private fun logoutUser() {
        // Limpiar SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Redirigir al LoginFragment
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    private fun fetchUserData() {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("logged_in_user_email", null)

        if (email != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getUserByEmail(email).collect { user ->
                    if (user != null) {
                        binding.userName.text = user.name
                        binding.userSurname.text = user.surname
                        binding.userEmail.text = user.email
                        binding.userPhone.text = user.phone
                    } else {
                        Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}