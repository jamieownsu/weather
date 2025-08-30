package com.chalupin.weather.domain.usecase

import com.chalupin.weather.domain.entity.LocationEntity
import com.chalupin.weather.domain.repository.LocationRepository
import com.chalupin.weather.domain.usecase.params.AddLocationParams
import com.chalupin.weather.domain.util.LocationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(params: AddLocationParams): LocationResponse<LocationEntity> {
        val locationEntity = LocationEntity(
            0,
            params.locationName,
            params.latitude,
            params.longitude
        )
        return withContext(ioDispatcher) {
            try {
                val location = locationRepository.insertLocation(locationEntity)
                LocationResponse.Success(location)
            } catch (e: Exception) {
                LocationResponse.Error(e)
            }
        }
    }
}