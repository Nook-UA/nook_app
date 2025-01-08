package com.es_g05.nook_app.repositories

import com.es_g05.nook_app.api.NookApiService
import com.es_g05.nook_app.models.NearbyParkingLot
import com.es_g05.nook_app.models.ParkingLotInformation

interface NookParksRepository {
    suspend fun getNearbyParks(lat: Double, lon: Double): List<NearbyParkingLot>
    suspend fun getParkInformation(parkId: Int): ParkingLotInformation
}

class NetworkNookParksRepository(
    private val nookApiService: NookApiService
) : NookParksRepository {
    override suspend fun getNearbyParks(
        lat: Double,
        lon: Double
    ): List<NearbyParkingLot> = nookApiService.getNearbyParks(lat = lat, lon = lon)

    override suspend fun getParkInformation(parkId: Int): ParkingLotInformation =
        nookApiService.getParkInfo(parkId = parkId)
}