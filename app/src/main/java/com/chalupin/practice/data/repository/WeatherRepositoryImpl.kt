package com.chalupin.practice.data.repository

import android.util.Log
import com.chalupin.practice.data.api.WeatherService
import com.chalupin.practice.data.mapper.toDomain
import com.chalupin.practice.domain.entity.Weather
import com.chalupin.practice.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
    ): Weather {
        try {
            val response = weatherService.getWeather(
                latitude = latitude,
                longitude = longitude,
                current = "temperature_2m",
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