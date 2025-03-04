package com.example.hotelbrowserandroid.data.remote.repositories


import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.api.RegisterRequest
import com.example.hotelbrowserandroid.data.remote.api.StrapiService
import com.example.hotelbrowserandroid.data.remote.api.UpdateUserRequest
import com.example.hotelbrowserandroid.data.remote.api.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val strapiService: StrapiService,
    @ApplicationContext private val context: Context
) {
    fun getUsers(): Flow<List<UserEntity>> {
        return flow {
            try {
                val response = strapiService.getUsers()
                val remoteUsers = response.data.map { it.attributes.toEntity() }
                emit(remoteUsers)
            } catch (e: Exception) {
                throw Exception("Error al obtener usuarios de Strapi", e)
            }
        }
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return try {
            val response = strapiService.getUsers()
            val user = response.data.firstOrNull {
                it.attributes.email == email && it.attributes.password == password
            }?.attributes?.toEntity()

            user ?: throw Exception("Credenciales incorrectas")
        } catch (e: Exception) {
            throw Exception("Error en la autenticación con Strapi", e)
        }
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return try {
            val response = strapiService.getUsers()
            val userWrapper = response.data.firstOrNull { it.attributes.email == email }

            if (userWrapper != null) {
                val userEntity = userWrapper.attributes.toEntity()
                userEntity.id = userWrapper.id // ✅ Asigna manualmente el ID de Strapi
                Log.d("UserRepository", "Usuario encontrado: ${userEntity.email}, ID: ${userEntity.id}")
                userEntity
            } else {
                Log.e("UserRepository", "Usuario no encontrado en Strapi para email: $email")
                null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al obtener usuario por email", e)
            null
        }
    }


    suspend fun updateUser(currentEmail: String, name: String, newEmail: String, phone: String) {
        try {
            val userId = strapiService.getUsers().data
                .firstOrNull { it.attributes.email == currentEmail }?.id
                ?: throw Exception("Usuario no encontrado")

            val updateRequest = mapOf(
                "name" to name,
                "email" to newEmail,
                "phone" to phone
            )

            strapiService.updateUser(userId, updateRequest)
        } catch (e: Exception) {
            throw Exception("Error al actualizar usuario en Strapi", e)
        }
    }

    suspend fun insertUser(user: UserEntity) {
        try {
            val registerRequest = RegisterRequest(
                data = UserData(
                    name = user.name,
                    surname = user.surname,
                    email = user.email,
                    phone = user.phone,
                    imgUrl = user.imgUrl,
                    password = user.password
                )
            )

            strapiService.addUser(registerRequest)
            Log.d("UserRepository", "Usuario registrado en Strapi correctamente")
        } catch (e: Exception) {
            throw Exception("Error al registrar usuario en Strapi", e)
        }
    }

    suspend fun uploadImageAndUpdateUser(imageUri: Uri, userId: Int): Boolean {
        return try {
            val imageFile = uriToFile(imageUri, context)
            val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("files", imageFile.name, requestFile)

            val response = strapiService.uploadImage(imagePart)

            if (response.isSuccessful) {
                val userData = response.body()
                val imageUrl = userData!!.attributes.imgUrl?:""

                strapiService.updateUser(
                    userId,
                    mapOf("imgUrl" to imageUrl)
                )
            } else {
                Log.e("UserRepository", "Error en la subida de la imagen: ${response.errorBody()?.string()}")
            }
            false
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al subir imagen y actualizar usuario", e)
            false
        }
    }




    private fun uriToFile(uri: Uri, context: Context): File {
        return try {
            val contentResolver = context.contentResolver
            val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
            tempFile.outputStream().use { outputStream ->
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            tempFile
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al convertir URI a archivo", e)
            throw Exception("Error al manejar el archivo de imagen")
        }
    }

}

