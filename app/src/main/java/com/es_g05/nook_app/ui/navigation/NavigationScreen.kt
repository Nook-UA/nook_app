package com.es_g05.nook_app.ui.navigation

import android.location.Location
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.es_g05.nook_app.auth.GoogleAuthUiClient
import com.es_g05.nook_app.ui.screens.MapScreen
import com.es_g05.nook_app.ui.screens.ProfileScreen
import com.es_g05.nook_app.view_models.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavigationScreen(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    fusedLocationClient: FusedLocationProviderClient
) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Home.path
    ) {
        composable(NavItem.Home.path) {
            val localContext = LocalContext.current
            val mapViewModel: MapViewModel = viewModel()

            val locationPermissions = rememberMultiplePermissionsState(
                permissions = listOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

            LaunchedEffect(true) {
                locationPermissions.launchMultiplePermissionRequest()
                // get the last known location
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        mapViewModel.updateLocation(location)
                    } else {
                        Toast.makeText(
                            localContext,
                            "Location not found",
                            Toast.LENGTH_LONG
                        ).show()
                        locationPermissions.launchMultiplePermissionRequest()
                    }
                }
            }
            MapScreen(
                viewModel = mapViewModel
            )
        }

        composable(NavItem.Profile.path) {
            googleAuthUiClient.getSignedInUser()?.let {
                ProfileScreen(
                    userData = googleAuthUiClient.getSignedInUser(),
                    onSignOut = onSignOut
                )
            }
        }
    }
}