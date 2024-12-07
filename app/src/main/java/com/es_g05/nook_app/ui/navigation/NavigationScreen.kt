package com.es_g05.nook_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.es_g05.nook_app.auth.GoogleAuthUiClient
import com.es_g05.nook_app.ui.screens.MapScreen
import com.es_g05.nook_app.ui.screens.ProfileScreen

@Composable
fun NavigationScreen(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Home.path
    ) {
        composable(NavItem.Home.path) { MapScreen() }

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