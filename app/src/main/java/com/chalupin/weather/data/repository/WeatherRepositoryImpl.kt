package com.chalupin.weather.data.repository

import android.util.Log
import com.chalupin.weather.data.api.WeatherService
import com.chalupin.weather.data.mapper.toDomain
import com.chalupin.weather.domain.entity.WeatherEntity
import com.chalupin.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
    ): WeatherEntity {
        try {
            val response = weatherService.getWeather(
                latitude = latitude,
                longitude = longitude,
                current = "temperature_2m,weather_code",
                daily = "temperature_2m_min,temperature_2m_max,weather_code",
                timezone = "America/New_York"
            )
            return response.toDomain()
        } catch (e: Exception) {
            Log.e("getWeatherData", e.message.toString())
            throw e
        }
    }
}