package com.es_g05.nook_app.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems = listOf(NavItem.Profile, NavItem.Map)
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val haptic = LocalHapticFeedback.current

    NavigationBar(
        containerColor = Color.Black
    ) { navItems.forEachIndexed { index, item ->
        NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.LightGray,
                selectedIconColor = Color.Black
            ),
            alwaysShowLabel = true,
            icon = { Icon(item.icon, contentDescription = item.title) },
            label = { Text(item.title) },
            selected = selectedItem == index,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                selectedItem = index

                // Navigate to the selected item's path
                navController.navigate(item.path) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) { saveState = true }
                    }
                    launchSingleTop = true
                }
            }
        )
    }}
}