package com.chalupin.practice.domain.repository

import com.chalupin.practice.domain.entity.Weather
import com.chalupin.practice.domain.util.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherData(): WeatherResponse<Weather>
}