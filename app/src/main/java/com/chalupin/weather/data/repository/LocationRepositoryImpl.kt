package com.chalupin.weather.data.repository

import android.util.Log
import com.chalupin.weather.data.dao.LocationDao
import com.chalupin.weather.data.mapper.toDomain
import com.chalupin.weather.data.mapper.toEntity
import com.chalupin.weather.domain.entity.UserLocation
import com.chalupin.weather.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepository {

    override suspend fun getCurrentLocation(): UserLocation {
        return try {
//            val location = fusedLocationClient.lastLocation.await()
            val cancellationTokenSource = CancellationTokenSource()
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await()
            UserLocation(
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

    override suspend fun getLocations(): List<UserLocation> {
        try {
            val locations = locationDao.getAllLocations()
            val locationsData = locations.map { it.toDomain() }
            return locationsData
        } catch (e: Exception) {
            Log.e("getLocations", e.message.toString())
            throw e
        }
    }

    override suspend fun insertLocation(userLocation: UserLocation): UserLocation {
        try {
            val id = locationDao.insertLocation(userLocation.toEntity())
            val updateLocation = userLocation.copy(id = id)
            return updateLocation
        } catch (e: Exception) {
            Log.e("insertLocation", e.message.toString())
            throw e
        }
    }

    override suspend fun deleteLocation(userLocation: UserLocation) {
        try {
            val locToDel = userLocation.toEntity()
            locationDao.deleteLocation(locToDel)
        } catch (e: Exception) {
            Log.e("deleteLocation", e.message.toString())
            throw e
        }
    }
}