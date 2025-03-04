package com.example.hotelbrowserandroid.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.remote.api.StrapiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CameraFragment : Fragment() {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private var photoUri: Uri? = null
    @Inject
    lateinit var apiService: StrapiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivityResultLaunchers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        view.findViewById<Button>(R.id.captureButton).setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }
        return view
    }

    private fun setupActivityResultLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                photoUri?.let {
                    uploadImageToStrapi(File(it.path ?: return@let))
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("profile_photo", it)
                    findNavController().popBackStack()
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
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        val photoFile = File(requireContext().cacheDir, "temp_image.jpg")
        photoUri = FileProvider.getUriForFile(requireContext(), "com.example.hotelbrowserandroid.fileprovider", photoFile)
        cameraLauncher.launch(photoUri!!)
    }

    private fun uploadImageToStrapi(photoFile: File) {
        val requestFile = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("files", photoFile.name, requestFile)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.uploadImage(body)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("UPLOAD_SUCCESS", "Imagen subida: $responseBody")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Imagen subida con éxito", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("UPLOAD_ERROR", "Error en la respuesta: ${response.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al subir imagen", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("UPLOAD_EXCEPTION", "Excepción: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

