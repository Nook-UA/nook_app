package com.es_g05.nook_app.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.es_g05.nook_app.R
import com.es_g05.nook_app.managers.RequestLocationPermission
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun MapScreen() {

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cameraPositionState = rememberCameraPositionState()

    val park1 = LatLng(40.640289, -8.651647)
    val park2 = LatLng(40.6388053454105, -8.652191713773961)
    val park3 = LatLng(40.64186580433356, -8.656481365804282)
    val park4 = LatLng(40.63451824048593, -8.656807479713201)

    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Request location permission and get last known location
    RequestLocationPermission {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val latLng = LatLng(location.latitude, location.longitude)
                userLocation = latLng
                cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15F)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = true),
                properties = MapProperties(isMyLocationEnabled = userLocation != null)
            ) {
                userLocation?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "You are here"
                    )
                    MarkerComposable(
                        state = MarkerState(position = park1)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location_marker),
                            contentDescription = "",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    MarkerComposable(
                        state = MarkerState(position = park2)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location_marker),
                            contentDescription = "",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    MarkerComposable(
                        state = MarkerState(position = park3)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location_marker),
                            contentDescription = "",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    MarkerComposable(
                        state = MarkerState(position = park4)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location_marker),
                            contentDescription = "",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp).align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .shadow(6.dp, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                ) {
                    IconButton(
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "",
                    modifier = Modifier.size(64.dp)
                )
            }
            SearchBar(
                query = "",
                onQueryChange = { },
                onSearch = { },
                active = false,
                onActiveChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter), // Align SearchBar at the bottom of the screen
                placeholder = { Text("Search for parks") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu Icon Search Bar"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            ) { }
        }
    }
}