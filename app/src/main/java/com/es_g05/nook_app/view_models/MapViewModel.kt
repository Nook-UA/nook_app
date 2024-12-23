package com.es_g05.nook_app.view_models

import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.es_g05.nook_app.repositories.NookParksRepository
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModelFactory(
    private val nookParksRepository: NookParksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(nookParksRepository = nookParksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MapViewModel(
    private val nookParksRepository: NookParksRepository
): ViewModel() {

    private var _location = MutableStateFlow<Location>(
        Location("DEFAULT_LOCATION").apply {
            latitude = 40.638076
            longitude = -8.653603
        }
    )

    val location: StateFlow<Location> = _location
    val properties = mutableStateOf(MapProperties(mapType = MapType.NORMAL))

    init {
        getNearbyParks()
    }

    fun updateLocation(location: Location) {
        _location.value = location
        getNearbyParks()
    }

    fun getCoordinates(): Location {
        return _location.value
    }

    fun getNearbyParks() {
        viewModelScope.launch {
            try {
                val parks = nookParksRepository.getNearbyParks(
                    _location.value.latitude,
                    _location.value.longitude
                )
                println("Nearby Parks: $parks")
            } catch (e: Exception) {
                println("Error fetching parks: ${e.message}")
            }
        }
    }
}