package com.chalupin.practice.data.repository

import android.util.Log
import com.chalupin.practice.data.database.dao.LocationDao
import com.chalupin.practice.data.mapper.toDomain
import com.chalupin.practice.data.mapper.toEntity
import com.chalupin.practice.domain.entity.Location
import com.chalupin.practice.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao
) : LocationRepository {
    override suspend fun getLocations(): List<Location> {
        try {
            val locations = locationDao.getAllLocations()
            val locationsData = locations.map { it.toDomain() }
            return locationsData
        } catch (e: Exception) {
            Log.e("getLocations", e.message.toString())
            throw e
        }
    }

    override suspend fun insertLocation(location: Location): Location {
        try {
            val id = locationDao.insertLocation(location.toEntity())
            val updateLocation = location.copy(id = id)
            return updateLocation
        } catch (e: Exception) {
            Log.e("insertLocation", e.message.toString())
            throw e
        }
    }

    override suspend fun deleteLocation(location: Location) {
        try {
            val locToDel = location.toEntity()
            locationDao.deleteLocation(locToDel)
        } catch (e: Exception) {
            Log.e("deleteLocation", e.message.toString())
            throw e
        }
    }
}