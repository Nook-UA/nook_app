package com.es_g05.nook_app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.es_g05.nook_app.ui.components.ParkInformation
import com.es_g05.nook_app.ui.components.ParkMarkerComposable
import com.es_g05.nook_app.view_models.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.math.roundToInt

@SuppressLint("MissingPermission", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(
    viewModel: MapViewModel
) {

    val properties = viewModel.properties.value
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    val userLocation = viewModel.getCoordinates()

    val park1 = LatLng(40.640289, -8.651647)
    val park2 = LatLng(40.6388053454105, -8.652191713773961)
    val park3 = LatLng(40.64186580433356, -8.656481365804282)
    val park4 = LatLng(40.63451824048593, -8.656807479713201)

    val parks = listOf(park1, park2, park3, park4)
    val selectedPark = remember { mutableStateOf<LatLng?>(null) }
    val isVisible = remember { mutableStateOf(true) }
    val offsetX = remember { mutableFloatStateOf(0f) }

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
            onMapClick = {
                selectedPark.value = null
            }
        ) {
            Marker(
                state = MarkerState(
                    position = LatLng(
                        userLocation.latitude,
                        userLocation.longitude
                    )
                ),
            )
            parks.forEach {park ->
                ParkMarkerComposable(
                    position = park,
                    onClick = {
                        selectedPark.value = park
                    }
                )
            }
        }
        selectedPark.value?.let { park ->
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .align(Alignment.BottomCenter)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = {_, dragAmount ->
                                offsetX.value += dragAmount
                            },
                            onDragEnd = {
                                if (offsetX.floatValue > 300 || offsetX.floatValue < -300) {
                                    isVisible.value = false
                                    selectedPark.value = null
                                    offsetX.floatValue = 0f
                                } else {
                                    offsetX.floatValue = 0f
                                }
                            }
                        )
                    }
                    .offset{ IntOffset(offsetX.floatValue.roundToInt(), 0) }
            ) {
                ParkInformation(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    park = park
                )
            }
        }
    }
}