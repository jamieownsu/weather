package com.chalupin.weather.domain.util

sealed class LocationResponse<out T> {
    data class Success<out T>(val location: T) : LocationResponse<T>()
    data class Error(val exception: Exception) : LocationResponse<Nothing>()
}