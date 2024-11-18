package com.es_g05.nook_app.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.es_g05.nook_app.auth.GoogleAuthUiClient
import com.es_g05.nook_app.ui.screens.MapScreen
import com.es_g05.nook_app.ui.screens.ProfileScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavigationScreen(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Profile.path
    ) {
        composable(NavItem.Profile.path) { googleAuthUiClient.getSignedInUser()
            ?.let {
                ProfileScreen(
                    userData = googleAuthUiClient.getSignedInUser(),
                    onSignOut = onSignOut
                )
            }
        }

        composable(NavItem.Map.path) { MapScreen() }
    }
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(navController = navController, onSignOut = onSignOut)
        },
        modifier = Modifier.width(230.dp),
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        scrimColor = Color.Black
    ) { }
}