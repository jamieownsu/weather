package com.chalupin.practice.domain.usecase

import com.chalupin.practice.domain.repository.LocationRepository
import com.chalupin.practice.domain.usecase.params.RemoveLocationParams
import com.chalupin.practice.domain.util.LocationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(params: RemoveLocationParams): LocationResponse<Unit> {
        return withContext(ioDispatcher) {
            try {
                LocationResponse.Success(locationRepository.deleteLocation(params.userLocation))
            } catch (e: Exception) {
                LocationResponse.Error(e)
            }
        }
    }
}