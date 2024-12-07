package com.es_g05.nook_app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.es_g05.nook_app.auth.GoogleAuthUiClient
import com.es_g05.nook_app.ui.navigation.AppBar
import com.es_g05.nook_app.ui.navigation.DrawerContent
import com.es_g05.nook_app.ui.navigation.NavigationScreen
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                navController = navController,
                onSignOut = onSignOut,
                drawerState = drawerState,
                scope = scope,
                userData = googleAuthUiClient.getSignedInUser()
            )
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        content = {
            Scaffold(
                topBar = {
                    AppBar(
                        onNavigationClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu Icon",
                                tint = Color.Black
                            )
                        }
                    )
                }
            ) {
                NavigationScreen(
                    navController = navController,
                    onSignOut = onSignOut,
                    googleAuthUiClient = googleAuthUiClient,
                    fusedLocationClient = fusedLocationProviderClient
                )
            }
        }
    )
}