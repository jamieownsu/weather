package com.chalupin.weather.domain.entity


data class DailyEntity(
    val date: List<String> = emptyList(),
    val temperatureMin: List<Double> = emptyList(),
    val temperatureMax: List<Double> = emptyList(),
    val weatherCode: List<Int> = emptyList()
)