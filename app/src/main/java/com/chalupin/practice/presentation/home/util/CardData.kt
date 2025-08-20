package com.chalupin.practice.presentation.home.util

import com.chalupin.practice.domain.entity.UserLocation
import com.chalupin.practice.domain.entity.Weather

data class CardData(
    val userLocation: UserLocation,
    val weather: Weather?,
    val isLoading: Boolean = false,
    val error: String? = null
)
