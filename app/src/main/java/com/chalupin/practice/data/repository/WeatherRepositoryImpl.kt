package com.chalupin.practice.data.repository

import com.chalupin.practice.data.api.WeatherService
import com.chalupin.practice.domain.entity.Weather
import com.chalupin.practice.domain.repository.WeatherRepository
import com.chalupin.practice.domain.util.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {
    override suspend fun getWeatherData(): WeatherResponse<Weather> {
        TODO("Not yet implemented")
    }
}