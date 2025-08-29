package com.chalupin.weather.domain.usecase

import com.chalupin.weather.domain.entity.UserLocation
import com.chalupin.weather.domain.repository.LocationRepository
import com.chalupin.weather.domain.util.LocationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): LocationResponse<UserLocation> {
        return withContext(ioDispatcher) {
            try {
                val userLocation = locationRepository.getCurrentLocation()
                LocationResponse.Success(userLocation)
            } catch (e: Exception) {
                LocationResponse.Error(e)
            }
        }
    }
}
