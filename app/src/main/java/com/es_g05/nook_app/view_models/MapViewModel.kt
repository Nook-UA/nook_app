package com.es_g05.nook_app.view_models

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.es_g05.nook_app.models.NearbyParkingLot
import com.es_g05.nook_app.repositories.NookParksRepository
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed interface NookUiState {
    data class Success(val parks: List<NearbyParkingLot>): NookUiState
    object Error: NookUiState
    object Loading: NookUiState
}

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

    var nookUiState: NookUiState by mutableStateOf(NookUiState.Loading)
        private set

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
            nookUiState = NookUiState.Loading
            nookUiState = try {
                NookUiState.Success(nookParksRepository.getNearbyParks(
                    lat = _location.value.latitude,
                    lon = _location.value.longitude
                ))
            } catch (e: IOException) {
                NookUiState.Error
            } catch (e: HttpException) {
                NookUiState.Error
            }
        }
    }
}