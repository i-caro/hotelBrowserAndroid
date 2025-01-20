package com.example.hotelbrowserandroid.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase.getDatabase(requireContext())

        // Fetch user data
        fetchUserData()
    }

    private fun fetchUserData() {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("logged_in_user_email", null)

        if (email != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                val user = appDatabase.userDao().getUserByEmail(email)
                if (user != null) {
                    binding.userName.text = user.name
                    binding.userEmail.text = user.email
                    binding.userPhone.text = user.phone
                } else {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show()
        }
    }
}