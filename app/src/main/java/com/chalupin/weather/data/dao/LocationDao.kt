package com.chalupin.weather.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chalupin.weather.data.dto.LocationDto

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationDto): Long

    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<LocationDto>

    @Delete
    suspend fun deleteLocation(location: LocationDto)
}