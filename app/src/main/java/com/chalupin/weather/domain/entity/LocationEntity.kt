package com.chalupin.weather.domain.entity

data class LocationEntity(
    val id: Long = -1L,
    val locationName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
) {
    companion object {
        fun createLocalWeatherLocation(): LocationEntity {
            return LocationEntity(locationName = "Local weather")
        }
    }
}