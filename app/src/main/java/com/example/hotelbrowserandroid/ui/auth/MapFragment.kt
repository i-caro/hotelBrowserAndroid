package com.example.hotelbrowserandroid.ui.auth

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.Address
import java.util.Locale

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private var serviceLocation: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        serviceLocation = arguments?.getString("service_location")

        val mapView = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapView.getMapAsync(this)

        view.findViewById<Button>(R.id.btnBack).setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        serviceLocation?.let { address ->
            geocodeAddress(address)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun geocodeAddress(address: String) {
        if (!Geocoder.isPresent()) {
            Log.e("mapa", "El servicio de Geocoder no está disponible en este dispositivo")
            return
        }

        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        geocoder.getFromLocationName(address, 1, object : Geocoder.GeocodeListener {
            override fun onGeocode(addresses: MutableList<android.location.Address>) {
                Log.d("mapa", "Resultados obtenidos: ${addresses.size}")

                if (addresses.isNotEmpty()) {
                    val location = addresses[0]
                    val latLng = LatLng(location.latitude, location.longitude)

                    Log.d("mapa", "Lat: ${location.latitude}, Lng: ${location.longitude}")

                    if (!::googleMap.isInitialized) {
                        Log.e("mapa", "El mapa aún no está listo")
                        return
                    }

                    requireActivity().runOnUiThread {
                        googleMap.addMarker(MarkerOptions().position(latLng).title("Ubicación del Servicio"))

                        Handler(Looper.getMainLooper()).postDelayed({
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        }, 500)
                    }
                } else {
                    Log.d("mapa", "No se encontró la dirección: $address")
                }
            }

            override fun onError(errorMessage: String?) {
                Log.e("mapa", "Error en la geocodificación: $errorMessage")
            }
        })
    }
}

