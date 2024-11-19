package com.example.hotelbrowserandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbrowserandroid.data.model.ReservaEntity
import com.example.hotelbrowserandroid.data.repository.ReservasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservaViewModel @Inject constructor(
    private val reservasRepository: ReservasRepository
) : ViewModel() {

    private val _reservas = MutableStateFlow<List<ReservaEntity>>(emptyList())
    val reservas: StateFlow<List<ReservaEntity>> get() = _reservas

    init {
        loadReservas()
    }

    private fun loadReservas() {
        viewModelScope.launch {
            val reservasList = reservasRepository.getAllReservas()
            _reservas.value = reservasList
        }
    }

    fun addReserva(reserva: ReservaEntity) {
        viewModelScope.launch {
            reservasRepository.insertReserva(reserva)
            loadReservas()
        }
    }

    fun getReservasByUser(userId: String): StateFlow<List<ReservaEntity>> {
        val userReservas = MutableStateFlow<List<ReservaEntity>>(emptyList())
        viewModelScope.launch {
            userReservas.value = reservasRepository.getReservasByUserId(userId)
        }
        return userReservas
    }
}