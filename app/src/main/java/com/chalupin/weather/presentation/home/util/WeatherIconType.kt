package com.chalupin.weather.presentation.home.util

sealed class WeatherIconType(open val res: Int) {
    data class WeatherIconStatic(override val res: Int) : WeatherIconType(res)
    data class WeatherIconAnimated(override val res: Int) : WeatherIconType(res)
}