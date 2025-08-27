package com.chalupin.weather.domain.usecase

import com.chalupin.weather.domain.entity.UserLocation
import com.chalupin.weather.domain.repository.LocationRepository
import com.chalupin.weather.domain.usecase.params.GetLocationsParams
import com.chalupin.weather.domain.util.LocationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(params: GetLocationsParams): LocationResponse<List<UserLocation>> {
        return withContext(ioDispatcher) {
            try {
                val locations = mutableListOf<UserLocation>()
                if (params.hasLocationPermission) {
                    val userLocation = locationRepository.getCurrentLocation()
                    locations.add(userLocation)
                }
                val storedLocations = locationRepository.getLocations()
                locations.addAll(storedLocations)
                LocationResponse.Success(locations)
            } catch (e: Exception) {
                LocationResponse.Error(e)
            }
        }
    }
}
