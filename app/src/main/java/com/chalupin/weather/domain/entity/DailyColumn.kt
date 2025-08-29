package com.chalupin.weather.domain.entity

import com.chalupin.weather.presentation.home.util.WeatherIconType

data class DailyColumn(
    val dayOfWeek: String,
    val maxTemp: String,
    val minTemp: String,
    val weatherTypeIcon: WeatherIconType
)