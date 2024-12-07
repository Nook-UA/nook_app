package com.es_g05.nook_app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.es_g05.nook_app.ui.components.ParkMarkerComposable
import com.es_g05.nook_app.view_models.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(
    viewModel: MapViewModel
) {

    val properties = viewModel.properties.value
    val uiSettings = viewModel.uiSettings.value
    val userLocation = viewModel.getCoordinates()

    val park1 = LatLng(40.640289, -8.651647)
    val park2 = LatLng(40.6388053454105, -8.652191713773961)
    val park3 = LatLng(40.64186580433356, -8.656481365804282)
    val park4 = LatLng(40.63451824048593, -8.656807479713201)

    val parks = listOf(park1, park2, park3, park4)


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
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
                properties = properties
            ) {
                userLocation.let {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                viewModel.getCoordinates().latitude,
                                viewModel.getCoordinates().longitude
                            )
                        )
                    )
                    parks.forEach {park ->
                        ParkMarkerComposable(position = park)
                    }
                }
            }
        }
    }
}