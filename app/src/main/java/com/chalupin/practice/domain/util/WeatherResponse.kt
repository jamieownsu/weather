package com.chalupin.practice.domain.util

sealed class WeatherResponse<out T> {
    data class Success<out T>(val weather: T) : WeatherResponse<T>()
    data class Error(val exception: Exception) : WeatherResponse<Nothing>()
}