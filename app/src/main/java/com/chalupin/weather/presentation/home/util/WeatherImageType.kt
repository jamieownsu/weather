package com.chalupin.weather.presentation.home.util

sealed class WeatherImageType(open val res: Int) {
    data class WeatherImageStatic(override val res: Int) : WeatherImageType(res)
    data class WeatherImageAnimated(override val res: Int) : WeatherImageType(res)
}