package com.example.hotelbrowserandroid.ui.auth

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import androidx.core.content.FileProvider
import com.example.hotelbrowserandroid.databinding.FragmentProfileBinding
import com.example.hotelbrowserandroid.ui.auth.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.io.File
import java.util.Date
import java.util.Locale
import android.os.Environment
import android.util.Log
import coil.load
import coil.transform.CircleCropTransformation

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()
    private var imageUri: Uri? = null
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActivityResultLaunchers()

        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.logoutButton.setOnClickListener {
            logoutUser()
        }

        binding.profileImageView.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }

        binding.btnChangePhoto.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_cameraFragment)
        }

        fetchUserData()
    }

    private fun setupActivityResultLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri?.let {
                    binding.profileImageView.setImageURI(it)
                    uploadImageToStrapi(it)
                }
            } else {
                Toast.makeText(requireContext(), "Captura cancelada", Toast.LENGTH_SHORT).show()
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = createImageFile()

        photoFile?.let {
            imageUri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", it)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            cameraLauncher.launch(intent)
        }
    }

    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
    }

    private fun uploadImageToStrapi(imageUri: Uri) {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null) ?: return

        viewModel.uploadProfileImage(imageUri, userId).observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Foto subida correctamente", Toast.LENGTH_SHORT).show()
                fetchUserData()
            } else {
                Toast.makeText(requireContext(), "Error al subir la imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchUserData() {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("logged_in_user_email", null)

        if (email != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                val user = viewModel.getUserByEmail(email)
                if (user != null) {
                    binding.userName.text = user.name
                    binding.userSurname.text = user.surname
                    binding.userEmail.text = user.email
                    binding.userPhone.text = user.phone

                    binding.profileImageView.load(user.imgUrl) {
                        crossfade(true)
                        placeholder(R.drawable.default_persona)
                        transformations(CircleCropTransformation())
                    }
                } else {
                    Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "No hay usuario logueado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logoutUser() {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        Toast.makeText(requireContext(), "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if( isGranted ){
            openCamera()
        } else {
            Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }
}