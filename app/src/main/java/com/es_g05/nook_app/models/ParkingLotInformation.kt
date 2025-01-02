package com.es_g05.nook_app.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParkingLotInformation(
    @SerialName("parking_lot_id")
    var parkingLotId: Int,
    @SerialName("image_url")
    var imageUrl: String,
    var occupancy: String
)