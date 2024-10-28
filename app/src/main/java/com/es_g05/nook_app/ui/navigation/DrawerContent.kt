package com.es_g05.nook_app.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DrawerContent(
    navController: NavController,
    onSignOut: () -> Unit,
) {
    val navItems = listOf(NavItem.Profile, NavItem.Map)
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Nook app",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 24.dp)
        )

        Spacer(modifier = Modifier.height(68.dp))

        navItems.forEachIndexed { _index, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(item.path) {
                            popUpTo(0)
                        }
                    }
            ) {
                Icon(
                    imageVector = when (item) {
                        NavItem.Profile -> Icons.Filled.Settings
                        NavItem.Map -> Icons.Filled.Map
                        else -> Icons.Default.Home
                    },
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSignOut() }
        ) {
            Icon(
                imageVector = Icons.Filled.Output,
                contentDescription = "Sign Out",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Sign Out",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}