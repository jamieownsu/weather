package com.chalupin.weather.domain.repository

import com.chalupin.weather.domain.entity.LocationEntity
import com.google.android.gms.tasks.CancellationToken

interface LocationRepository {
    suspend fun getCurrentLocation(cancellationToken: CancellationToken): LocationEntity

    suspend fun getLocations(): List<LocationEntity>

    suspend fun deleteLocation(locationEntity: LocationEntity)

    suspend fun insertLocation(locationEntity: LocationEntity): LocationEntity
}