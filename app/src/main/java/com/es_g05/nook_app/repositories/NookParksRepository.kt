package com.es_g05.nook_app.repositories

import com.es_g05.nook_app.api.NookApiService
import com.es_g05.nook_app.models.NearbyParkingLot

interface NookParksRepository {
    suspend fun getNearbyParks(lat: Double, lon: Double): List<NearbyParkingLot>
}

class NetworkNookParksRepository(
    private val nookApiService: NookApiService
) : NookParksRepository {
    override suspend fun getNearbyParks(
        lat: Double,
        lon: Double
    ): List<NearbyParkingLot> = nookApiService.getNearbyParks(lat = lat, lon = lon)
}