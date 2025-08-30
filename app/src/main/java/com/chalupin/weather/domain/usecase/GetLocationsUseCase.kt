package com.chalupin.weather.domain.usecase

import com.chalupin.weather.domain.entity.LocationEntity
import com.chalupin.weather.domain.repository.LocationRepository
import com.chalupin.weather.domain.util.LocationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): LocationResponse<List<LocationEntity>> {
        return withContext(ioDispatcher) {
            try {
                val locations = locationRepository.getLocations()
                LocationResponse.Success(locations)
            } catch (e: Exception) {
                LocationResponse.Error(e)
            }
        }
    }
}
