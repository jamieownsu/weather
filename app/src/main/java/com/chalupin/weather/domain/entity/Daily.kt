package com.chalupin.weather.domain.entity

import com.chalupin.weather.domain.enum.WeatherCodes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt



data class Daily(
    val date: List<String> = emptyList(),
    val temperatureMin: List<Double> = emptyList(),
    val temperatureMax: List<Double> = emptyList(),
    val weatherCode: List<Int> = emptyList()
)