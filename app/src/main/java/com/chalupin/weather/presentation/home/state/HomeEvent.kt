package com.chalupin.weather.presentation.home.state

import com.chalupin.weather.domain.entity.LocationEntity
import com.chalupin.weather.presentation.home.util.WeatherCardData

sealed class HomeEvent {
    data object AllowLocationPermissionEvent : HomeEvent()

    data object LoadLocationsEvent : HomeEvent()

    data object LoadUserLocationEvent : HomeEvent()

    data class GetWeatherEvent(val locationEntity: LocationEntity) : HomeEvent()

    data class AddLocationEvent(
        val locationName: String,
        val latitude: Double,
        val longitude: Double
    ) : HomeEvent()

    data class RemoveLocationEvent(val cardDataState: WeatherCardData) : HomeEvent()
}