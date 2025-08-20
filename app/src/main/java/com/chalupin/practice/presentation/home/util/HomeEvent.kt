package com.chalupin.practice.presentation.home.util

import com.chalupin.practice.domain.entity.UserLocation

sealed class HomeEvent {
    data class LoadLocationsEvent(val hasLocationPermission: Boolean) : HomeEvent()

    data class GetWeatherEvent(val userLocation: UserLocation) : HomeEvent()

    data class AddLocationEvent(
        val locationName: String,
        val latitude: Double,
        val longitude: Double
    ) : HomeEvent()

    data class RemoveLocationEvent(val cardData: CardData) : HomeEvent()
}