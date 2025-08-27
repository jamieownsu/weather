package com.chalupin.weather.presentation.home.util

import com.chalupin.weather.domain.entity.UserLocation

sealed class HomeEvent {
    data object AllowLocationPermissionEvent : HomeEvent()

    data object LoadLocationsEvent : HomeEvent()

    data class GetWeatherEvent(val userLocation: UserLocation) : HomeEvent()

    data class AddLocationEvent(
        val locationName: String,
        val latitude: Double,
        val longitude: Double
    ) : HomeEvent()

    data class RemoveLocationEvent(val cardData: CardData) : HomeEvent()
}