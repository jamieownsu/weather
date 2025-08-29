package com.chalupin.weather.presentation.home.state

import com.chalupin.weather.presentation.home.util.WeatherCardData

sealed class ModalState {
    object Hidden : ModalState()
    data class ShowDeleteConfirmation(val cardDataState: WeatherCardData) : ModalState()
}