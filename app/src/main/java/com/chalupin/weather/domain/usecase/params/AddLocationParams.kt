package com.chalupin.weather.domain.usecase.params

data class AddLocationParams(
    val locationName: String,
    val latitude: Double,
    val longitude: Double
)