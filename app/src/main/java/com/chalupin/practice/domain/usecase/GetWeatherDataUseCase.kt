package com.chalupin.practice.domain.usecase

import com.chalupin.practice.domain.entity.Weather
import com.chalupin.practice.domain.repository.WeatherRepository
import com.chalupin.practice.domain.util.WeatherResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): WeatherResponse<Weather> {
        return withContext(ioDispatcher) { weatherRepository.getWeatherData() }
    }
}