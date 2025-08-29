package com.chalupin.weather.domain.entity

data class DailyColumn(
    val date: String,
    val minTemp: String,
    val maxTemp: String,
    val animatedIcon: Int,
    val icon: Int
)