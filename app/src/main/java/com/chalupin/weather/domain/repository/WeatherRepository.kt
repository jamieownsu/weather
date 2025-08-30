package com.chalupin.weather.domain.repository

import com.chalupin.weather.domain.entity.WeatherEntity

interface WeatherRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
    ): WeatherEntity
}