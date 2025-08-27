package com.chalupin.weather.domain.repository

import com.chalupin.weather.domain.entity.UserLocation

interface LocationRepository {
    suspend fun getCurrentLocation(): UserLocation

    suspend fun getLocations(): List<UserLocation>

    suspend fun deleteLocation(userLocation: UserLocation)

    suspend fun insertLocation(userLocation: UserLocation): UserLocation
}