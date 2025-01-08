package com.es_g05.nook_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.es_g05.nook_app.R
import com.es_g05.nook_app.models.NearbyParkingLot
import com.es_g05.nook_app.ui.theme.errorLight
import com.es_g05.nook_app.ui.theme.primaryContainerDark
import com.es_g05.nook_app.view_models.ParkInfoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkInformation(
    park: NearbyParkingLot,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    parkInfoUiState: ParkInfoUiState,
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {

        if (parkInfoUiState is ParkInfoUiState.Loading) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    color = primaryContainerDark,
                    modifier = Modifier.align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        } else if (parkInfoUiState is ParkInfoUiState.Success) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Text(
                        text = "Park details",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                    )
                }

                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    ) {
                        AsyncImage(
                            model = park.picture,
                            contentDescription = park.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = park.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                                .background(
                                    color = Color(0xAAFFAC75),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        ParkingSpotsInformation(
                            parkOccupation = if (parkInfoUiState.parkInfo.occupancy?.freed != null) {
                                parkInfoUiState.parkInfo.occupancy?.freed.toString()
                            } else {
                                "Not Available"
                            },
                            label = "Available Parking Spots: ",
                            icon = Icons.Filled.CheckCircle,
                            iconColor = Color(0xff037f51)
                        )

                        ParkingSpotsInformation(
                            parkOccupation = if (parkInfoUiState.parkInfo.occupancy?.occupied != null) {
                                parkInfoUiState.parkInfo.occupancy?.occupied.toString()
                            } else {
                                "Not Available"
                            },
                            label = "Occupied Parking Spots: ",
                            icon = Icons.Rounded.RemoveCircle,
                            iconColor = errorLight
                        )

                        ParkingSpotsInformation(
                            parkOccupation = if (parkInfoUiState.parkInfo.occupancy?.total != null) {
                                parkInfoUiState.parkInfo.occupancy?.total.toString()
                            } else {
                                "Not Available"
                            },
                            label = "Total Parking Spots: ",
                            icon = Icons.Filled.LocalParking,
                            iconColor = primaryContainerDark
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ParkingSpotsInformation(
    parkOccupation: String,
    label: String,
    icon: ImageVector,
    iconColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            tint = iconColor
        )

        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = parkOccupation,
            fontSize = 16.sp,
            color = Color.Black,
        )
    }
}