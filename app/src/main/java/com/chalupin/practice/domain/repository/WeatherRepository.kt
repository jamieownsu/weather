package com.chalupin.practice.domain.repository

import com.chalupin.practice.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
    ): Weather
}