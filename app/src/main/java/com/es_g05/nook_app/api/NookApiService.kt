package com.es_g05.nook_app.api

import com.es_g05.nook_app.models.NearbyParkingLot
import retrofit2.http.GET
import retrofit2.http.Query

interface NookApiService {
    @GET("park/nearby")
    suspend fun getNearbyParks(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("max_dist") maxDist: Double = 1.0
    ): List<NearbyParkingLot>
}
