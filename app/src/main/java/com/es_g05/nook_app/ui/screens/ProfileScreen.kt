package com.es_g05.nook_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.es_g05.nook_app.R
import com.es_g05.nook_app.auth.UserData

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.profile_screen,
            ),
            contentDescription = "Profile Screen",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (userData?.profilePictureUrl != null) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (userData?.userName != null) {
                    Text(
                        text = userData.userName,
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(onClick = onSignOut) {
                    Text("Sign out")
                }
            }
        }
    }
}