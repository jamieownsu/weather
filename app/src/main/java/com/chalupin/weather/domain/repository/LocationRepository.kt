package com.chalupin.weather.domain.repository

import com.chalupin.weather.domain.entity.UserLocation
import com.google.android.gms.tasks.CancellationToken

interface LocationRepository {
    suspend fun getCurrentLocation(cancellationToken: CancellationToken): UserLocation

    suspend fun getLocations(): List<UserLocation>

    suspend fun deleteLocation(userLocation: UserLocation)

    suspend fun insertLocation(userLocation: UserLocation): UserLocation
}