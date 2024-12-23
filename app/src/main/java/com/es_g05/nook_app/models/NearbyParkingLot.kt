package com.es_g05.nook_app.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NearbyParkingLot(
    val id: Int,
    val name: String,
    val picture: String?,
    val latitude: Double,
    val longitude: Double,
    @SerialName(value = "rtsp_url")
    val rtspUrl: String?,
    val distance: Double
)
