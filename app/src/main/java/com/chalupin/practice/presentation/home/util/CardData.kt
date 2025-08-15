package com.chalupin.practice.presentation.home.util

import com.chalupin.practice.domain.entity.Location
import com.chalupin.practice.domain.entity.Weather

data class CardData(
    val location: Location,
    val weather: Weather?,
    val isLoading: Boolean = false,
    val error: String? = null
)
