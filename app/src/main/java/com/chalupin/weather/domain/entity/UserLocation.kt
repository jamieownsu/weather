package com.chalupin.weather.domain.entity

data class UserLocation(
    val id: Long,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
)