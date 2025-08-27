package com.chalupin.weather.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chalupin.weather.data.dto.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity): Long

    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<LocationEntity>

    @Delete
    suspend fun deleteLocation(location: LocationEntity)
}