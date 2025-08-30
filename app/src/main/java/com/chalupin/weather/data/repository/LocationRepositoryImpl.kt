package com.chalupin.weather.data.repository

import android.util.Log
import com.chalupin.weather.data.dao.LocationDao
import com.chalupin.weather.data.mapper.toDomain
import com.chalupin.weather.data.mapper.toEntity
import com.chalupin.weather.domain.entity.LocationEntity
import com.chalupin.weather.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepository {

    override suspend fun getCurrentLocation(cancellationToken: CancellationToken): LocationEntity {
        return try {
//            val location = fusedLocationClient.lastLocation.await()
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationToken
            ).await()
            LocationEntity(
                -1,
                "Local weather",
                location?.latitude ?: 40.712776,
                location?.longitude ?: -74.005974,
            )
        } catch (e: SecurityException) {
            Log.e("getCurrentLocation", e.message.toString())
            throw e
        } catch (e: Exception) {
            Log.e("getCurrentLocation", e.message.toString())
            throw e
        }
    }

    override suspend fun getLocations(): List<LocationEntity> {
        try {
            val locations = locationDao.getAllLocations()
            val locationsData = locations.map { it.toDomain() }
            return locationsData
        } catch (e: Exception) {
            Log.e("getLocations", e.message.toString())
            throw e
        }
    }

    override suspend fun insertLocation(locationEntity: LocationEntity): LocationEntity {
        try {
            val id = locationDao.insertLocation(locationEntity.toEntity())
            val updateLocation = locationEntity.copy(id = id)
            return updateLocation
        } catch (e: Exception) {
            Log.e("insertLocation", e.message.toString())
            throw e
        }
    }

    override suspend fun deleteLocation(locationEntity: LocationEntity) {
        try {
            val locToDel = locationEntity.toEntity()
            locationDao.deleteLocation(locToDel)
        } catch (e: Exception) {
            Log.e("deleteLocation", e.message.toString())
            throw e
        }
    }
}