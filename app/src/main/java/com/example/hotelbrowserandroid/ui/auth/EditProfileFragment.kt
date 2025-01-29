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
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.databinding.FragmentEditProfileBinding
import com.example.hotelbrowserandroid.ui.auth.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("logged_in_user_email", null)

        if (email != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getUserByEmail(email).collect { user ->
                    if (user != null) {
                        binding.editName.setText(user.name)
                        binding.editSurname.setText(user.surname)
                        binding.editEmail.setText(user.email)
                        binding.editPhone.setText(user.phone)
                    } else {
                        Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.saveChangesButton.setOnClickListener {
            val newName = binding.editName.text.toString()
            val newEmail = binding.editEmail.text.toString()
            val newPhone = binding.editPhone.text.toString()

            if (newName.isNotBlank() && newEmail.isNotBlank() && newPhone.isNotBlank()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.updateUser(email!!, newName, newEmail, newPhone)
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}