package com.es_g05.nook_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.es_g05.nook_app.R
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState

@Composable
fun ParkMarkerComposable(
    position: LatLng,
    onClick: () -> Unit
) {
    MarkerComposable(
        state = MarkerState(position = position),
        onClick = {
            onClick()
            true },
        content = {
            Image(
                painter = painterResource(id = R.drawable.location_marker),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }
    )
}