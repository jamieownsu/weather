package com.chalupin.weather.presentation.home.util

import com.chalupin.weather.domain.entity.UserLocation
import com.chalupin.weather.domain.entity.Weather

data class CardData(
    val userLocation: UserLocation,
    val weather: Weather?,
    val isLoading: Boolean = false,
    val error: String? = null
)
