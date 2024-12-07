package com.es_g05.nook_app.view_models

import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(): ViewModel() {
    private var _location = MutableStateFlow<Location>(
        Location("DEFAULT_LOCATION").apply {
            latitude = 40.638076
            longitude = -8.653603
        }
    )

    val location: StateFlow<Location> = _location
    val uiSettings = mutableStateOf(MapUiSettings())
    val properties = mutableStateOf(MapProperties(mapType = MapType.NORMAL))

    init {
        viewModelScope.launch {
            val userLocation = getCoordinates()
            _location.emit(userLocation)
        }

    }

    fun updateLocation(location: Location) {
        this._location = MutableStateFlow(location)
    }

    fun getCoordinates(): Location {
        return _location.value
    }
}