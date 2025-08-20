package com.chalupin.practice.domain.usecase

import com.chalupin.practice.domain.entity.UserLocation
import com.chalupin.practice.domain.repository.LocationRepository
import com.chalupin.practice.domain.usecase.params.GetLocationsParams
import com.chalupin.practice.domain.util.LocationResponse
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
