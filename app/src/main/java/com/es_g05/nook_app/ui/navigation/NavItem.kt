package com.es_g05.nook_app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings

sealed class NavItem {
    object Home:
            Item(
                path = NavPath.HOME.toString(),
                title = NavTitle.HOME,
                icon = Icons.Default.Home
            )

    object Map:
            Item(
                path = NavPath.MAP.toString(),
                title = NavTitle.MAP,
                icon = Icons.Default.Map
            )

    object Profile:
            Item(
                path = NavPath.PROFILE.toString(),
                title = NavTitle.PROFILE,
                icon = Icons.Default.Settings
            )
}