package com.chalupin.practice.presentation.home.util

import com.chalupin.practice.domain.entity.Location

sealed class HomeEvent {


    data object LoadLocationsEvent : HomeEvent()

    data class GetWeatherEvent(val location: Location) : HomeEvent()

    data class AddLocationEvent(
        val locationName: String,
        val latitude: Double,
        val longitude: Double
    ) : HomeEvent()

    data class RemoveLocationEvent(val cardData: CardData) : HomeEvent()
}