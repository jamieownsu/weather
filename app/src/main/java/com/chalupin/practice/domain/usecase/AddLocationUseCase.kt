package com.chalupin.practice.domain.usecase

import com.chalupin.practice.domain.entity.Location
import com.chalupin.practice.domain.repository.LocationRepository
import com.chalupin.practice.domain.usecase.params.AddLocationParams
import com.chalupin.practice.domain.util.LocationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(params: AddLocationParams): LocationResponse<Location> {
        val location = Location(
            0,
            params.locationName,
            params.latitude,
            params.longitude
        )
        return withContext(ioDispatcher) {
            try {
                val location = locationRepository.insertLocation(location)
                LocationResponse.Success(location)
            } catch (e: Exception) {
                LocationResponse.Error(e)
            }
        }
    }
}