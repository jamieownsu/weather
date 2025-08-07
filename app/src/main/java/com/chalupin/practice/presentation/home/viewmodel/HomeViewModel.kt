package com.chalupin.practice.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalupin.practice.domain.usecase.GetWeatherDataUseCase
import com.chalupin.practice.domain.util.WeatherResponse
import com.chalupin.practice.presentation.home.util.HomeEvent
import com.chalupin.practice.presentation.home.util.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        handleEvent(HomeEvent.LoadWeatherEvent)
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadWeatherEvent -> fetchListings()
        }
    }

    private fun fetchListings() {
        viewModelScope.launch {
            _uiState.value = HomeState.Loading
            try {
                when (val result = getWeatherDataUseCase()) {
                    is WeatherResponse.Success -> {
                        _uiState.value = HomeState.Success(result.data)
                    }

                    is WeatherResponse.Error -> {
                        _uiState.value = HomeState.Error(result.exception)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = HomeState.Error(e)
            }
        }
    }
}