package com.chalupin.practice.presentation.home.util

import com.chalupin.practice.domain.entity.Weather

sealed class HomeState() {
    object Loading : HomeState()

    data class Success(val weather: Weather) : HomeState()

    data class Error(val exception: Exception) : HomeState()
}