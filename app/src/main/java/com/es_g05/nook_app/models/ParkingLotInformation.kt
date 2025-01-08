package com.es_g05.nook_app.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParkingLotInformation(
    @SerialName("parking_lot_id")
    var parkingLotId: String? = null,
    @SerialName("image_url")
    var imageUrl: String? = null,
    var occupancy: Occupancy? = null,
    var detail: String? = null
)

@Serializable
data class Occupancy(
    var freed: Int? = null,
    var occupied: Int? = null,
    var total: Int? = null
)