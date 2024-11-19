package com.example.hotelbrowserandroid.ui.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.hotelbrowserandroid.data.model.ReservaEntity
import com.example.hotelbrowserandroid.data.model.ServicioEntity
import com.example.hotelbrowserandroid.data.model.UserEntity
import kotlinx.coroutines.launch

@BindingAdapter("usuariosList")
fun bindUsuariosList(recyclerView: RecyclerView, stateFlow: StateFlow<List<UserEntity>>?) {
    val adapter = recyclerView.adapter
    if (adapter is UsuarioAdapter && stateFlow != null) {
        val lifecycleOwner = recyclerView.context as? LifecycleOwner
        lifecycleOwner?.lifecycleScope?.launch {
            stateFlow.collect { usuarios ->
                adapter.submitList(usuarios)
            }
        }
    }
}

@BindingAdapter("reservasList")
fun bindReservasList(recyclerView: RecyclerView, stateFlow: StateFlow<List<ReservaEntity>>?) {
    val adapter = recyclerView.adapter
    if (adapter is ReservasAdapter && stateFlow != null) {
        val lifecycleOwner = recyclerView.context as? LifecycleOwner
        lifecycleOwner?.lifecycleScope?.launch {
            stateFlow.collect { reservas ->
                adapter.submitList(reservas)
            }
        }
    }
}

@BindingAdapter("serviciosList")
fun bindServiciosList(recyclerView: RecyclerView, stateFlow: StateFlow<List<ServicioEntity>>?) {
    val adapter = recyclerView.adapter
    if (adapter is ServiciosAdapter && stateFlow != null) {
        val lifecycleOwner = recyclerView.context as? LifecycleOwner
        lifecycleOwner?.lifecycleScope?.launch {
            stateFlow.collect { servicios ->
                adapter.submitList(servicios)
            }
        }
    }
}