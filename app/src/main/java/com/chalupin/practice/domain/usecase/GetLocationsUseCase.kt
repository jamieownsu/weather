package com.chalupin.practice.domain.usecase

import com.chalupin.practice.domain.entity.Location
import com.chalupin.practice.domain.repository.LocationRepository
import com.chalupin.practice.domain.util.LocationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): LocationResponse<List<Location>> {
        return withContext(ioDispatcher) {
            try {
                LocationResponse.Success(locationRepository.getLocations())
            } catch (e: Exception) {
                LocationResponse.Error(e)
            }
        }
    }
}
