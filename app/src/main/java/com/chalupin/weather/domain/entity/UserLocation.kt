package com.chalupin.weather.domain.entity

data class UserLocation(
    val id: Long = -1L,
    val locationName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
) {
    companion object {
        fun createLocalWeatherLocation(): UserLocation {
            return UserLocation(locationName = "Local weather")
        }
    }
}