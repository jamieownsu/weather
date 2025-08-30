package com.chalupin.weather.presentation.home.util

data class DailyColumn(
    val dayOfWeek: String,
    val maxTemp: String,
    val minTemp: String,
    val weatherTypeIcon: WeatherIconType
)