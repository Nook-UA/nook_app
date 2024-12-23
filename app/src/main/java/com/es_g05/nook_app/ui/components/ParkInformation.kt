package com.es_g05.nook_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.es_g05.nook_app.R
import com.es_g05.nook_app.ui.theme.inverseOnSurfaceLight
import com.es_g05.nook_app.ui.theme.primaryLight
import com.google.android.gms.maps.model.LatLng

@Composable
fun ParkInformation(
    modifier: Modifier,
    park: LatLng
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .padding(12.dp)
            .wrapContentHeight()
            .shadow(6.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(inverseOnSurfaceLight)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(4.dp)
                    .align(Alignment.CenterVertically)
                    .drawBehind {
                        drawCircle(
                            color = primaryLight,
                            radius = 70.0f
                        )
                    },
                text = "A",
                fontSize = 24.sp
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Park Autocarro Bar",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Text(
                    "106 parking spots available",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Image(
                painter = painterResource(id = R.drawable.autocarro_bar),
                contentDescription = "Autocarro Bar's image",
                modifier = Modifier.size(70.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 20.dp,
                            bottomEnd = 20.dp,
                            bottomStart = 0.dp
                        )
                    )
            )
        }
    }
}