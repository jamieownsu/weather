package com.chalupin.weather.presentation.home.util

import com.chalupin.weather.domain.entity.LocationEntity
import com.chalupin.weather.domain.entity.WeatherEntity

data class WeatherCardData(
    val locationEntity: LocationEntity,
    val weatherEntity: WeatherEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)