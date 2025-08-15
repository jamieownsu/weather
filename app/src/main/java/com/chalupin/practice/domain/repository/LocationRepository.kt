package com.chalupin.practice.domain.repository

import com.chalupin.practice.domain.entity.Location

interface LocationRepository {
    suspend fun getLocations(): List<Location>

    suspend fun deleteLocation(location: Location)

    suspend fun insertLocation(location: Location): Location
}