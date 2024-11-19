package com.example.hotelbrowserandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbrowserandroid.data.model.ServicioEntity
import com.example.hotelbrowserandroid.data.repository.ServiciosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicioViewModel @Inject constructor(
    private val serviciosRepository: ServiciosRepository
) : ViewModel() {

    private val _servicios = MutableStateFlow<List<ServicioEntity>>(emptyList())
    val servicios: StateFlow<List<ServicioEntity>> get() = _servicios

    init {
        loadServicios()
    }

    private fun loadServicios() {
        viewModelScope.launch {
            val serviciosList = serviciosRepository.getAllServicios()
            _servicios.value = serviciosList
        }
    }
}