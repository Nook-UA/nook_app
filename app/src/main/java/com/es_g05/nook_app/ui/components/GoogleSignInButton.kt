package com.es_g05.nook_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.es_g05.nook_app.R

@Composable
fun GoogleSignInButton(onSignInClick: () -> Unit) {
    Button(
        onClick = onSignInClick,
        modifier = Modifier.shadow(
            elevation = 3.dp,
            shape = RoundedCornerShape(8.dp),
        ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp).background(Color.White),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.google_logo),
                contentDescription = "Google Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(20.dp).height(20.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(
                text = "Login with Google",
                fontSize = 12.sp,
            )
        }
    }
}