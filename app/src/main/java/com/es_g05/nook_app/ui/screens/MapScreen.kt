package com.es_g05.nook_app.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.es_g05.nook_app.models.NearbyParkingLot
import com.es_g05.nook_app.ui.components.ParkInformation
import com.es_g05.nook_app.ui.components.ParkMarkerComposable
import com.es_g05.nook_app.ui.theme.primaryContainerDark
import com.es_g05.nook_app.view_models.MapViewModel
import com.es_g05.nook_app.view_models.NookUiState
import com.es_g05.nook_app.view_models.ParkInfoUiState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(
    viewModel: MapViewModel
) {

    val uiState = viewModel.nookUiState
    val parkInfoUiState = viewModel.parkInfoUiState
    val properties = viewModel.properties.value
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    val userLocation = viewModel.getCoordinates()
    val selectedPark = remember { mutableStateOf<NearbyParkingLot?>(null) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    var locationSelectionMode by rememberSaveable { mutableStateOf(false) }
    var selectedLocation by rememberSaveable { mutableStateOf<LatLng?>(null) }

    // Observe changes in uiState to trigger Toast Messages for errors
    val context = LocalContext.current
    LaunchedEffect(uiState) {
        if (uiState is NookUiState.Error) {
            Toast.makeText(context, "An error occurred while fetching Nearby Parks", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(parkInfoUiState) {
        if (parkInfoUiState is ParkInfoUiState.Error) {
            Toast.makeText(context, "An error occurred while fetching Park Information", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(selectedPark.value) {
        selectedPark.value?.let {
            viewModel.getParkInformation(it.id)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(
                    LatLng(
                        viewModel.getCoordinates().latitude,
                        viewModel.getCoordinates().longitude
                    ),
                    15f
                )
            },
            uiSettings = uiSettings,
            properties = properties,
            onMapClick = { latLng ->
                if (locationSelectionMode) {
                    selectedLocation = latLng
                } else {
                    selectedPark.value = null
                }
            }
        ) {
            if (!locationSelectionMode) {
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            userLocation.latitude,
                            userLocation.longitude
                        )
                    ),
                )
            }
            if (locationSelectionMode && selectedLocation != null) {
                selectedLocation?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Selected Location",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                    )
                }
            }
            if (uiState is NookUiState.Success) {
                uiState.parks.forEach { park ->
                    ParkMarkerComposable(
                        park = park,
                        onClick = {
                            selectedPark.value = park
                            showBottomSheet = true
                        }
                    )
                }
            }
        }
        selectedPark.value?.let { park ->
            ParkInformation(
                park = park,
                sheetState = sheetState,
                onDismiss = {
                    showBottomSheet = false
                    selectedPark.value = null
                },
                parkInfoUiState = parkInfoUiState
            )
        }

        FloatingActionButton(
            onClick = {
                if (locationSelectionMode) {
                    selectedLocation?.let {
                        viewModel.updateLocation(Location("").apply {
                            latitude = it.latitude
                            longitude = it.longitude
                        })
                    }
                }
                locationSelectionMode = !locationSelectionMode
                selectedLocation = null
            },
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (locationSelectionMode) Icons.Default.Check else Icons.Default.Place,
                contentDescription = if(locationSelectionMode) "Confirm Location" else "Select Location"
            )
        }

        if (uiState is NookUiState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))// Semi-transparent background
                    .clickable(enabled = false) {}, // Block touches
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = primaryContainerDark
                    )
                    Text(
                        text = "Loading Nearby Parks...",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }
    }
}