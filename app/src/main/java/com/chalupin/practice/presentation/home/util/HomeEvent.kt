package com.chalupin.practice.presentation.home.util

sealed class HomeEvent {
    data object LoadWeatherEvent : HomeEvent()
}