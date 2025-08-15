package com.chalupin.practice.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalupin.practice.domain.entity.Location
import com.chalupin.practice.domain.usecase.AddLocationUseCase
import com.chalupin.practice.domain.usecase.GetLocationsUseCase
import com.chalupin.practice.domain.usecase.GetWeatherDataUseCase
import com.chalupin.practice.domain.usecase.RemoveLocationUseCase
import com.chalupin.practice.domain.usecase.params.AddLocationParams
import com.chalupin.practice.domain.usecase.params.GetWeatherDataParams
import com.chalupin.practice.domain.usecase.params.RemoveLocationParams
import com.chalupin.practice.domain.util.LocationResponse
import com.chalupin.practice.domain.util.WeatherResponse
import com.chalupin.practice.presentation.home.util.CardData
import com.chalupin.practice.presentation.home.util.HomeEvent
import com.chalupin.practice.presentation.home.util.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val addLocationUseCase: AddLocationUseCase,
    private val removeLocationUseCase: RemoveLocationUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState(emptyList()))
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private val _snackBarChannel = Channel<String>()
    val snackBarFlow = _snackBarChannel.receiveAsFlow()

    init {
        handleEvent(HomeEvent.LoadLocationsEvent)
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadLocationsEvent -> loadLocations()
            is HomeEvent.GetWeatherEvent -> getWeatherData(event.location)
            is HomeEvent.AddLocationEvent -> addLocation(
                event.locationName,
                event.latitude,
                event.longitude
            )

            is HomeEvent.RemoveLocationEvent -> removeLocation(event.cardData)
        }
    }

    private fun loadLocations() {
        viewModelScope.launch {
            when (val result = getLocationsUseCase()) {
                is LocationResponse.Success -> {
                    val locations = result.location
                    _uiState.update { currentState ->
                        val items = locations.map { location ->
                            CardData(
                                location,
                                null,
                                isLoading = true
                            )
                        }
                        currentState.copy(weatherData = items)
                    }
                    locations.map { location ->
                        handleEvent(HomeEvent.GetWeatherEvent(location))
                    }
                }

                is LocationResponse.Error -> {
                    _snackBarChannel.send(result.exception.message.toString())
                }
            }
        }
    }

    private fun getWeatherData(location: Location) {
        viewModelScope.launch {
            val latitude = location.latitude
            val longitude = location.longitude
            val params = GetWeatherDataParams(latitude, longitude)
            _uiState.update { currentState ->
                val items = currentState.weatherData.map { cardData ->
                    if (cardData.location.id == location.id) {
                        cardData.copy(isLoading = true)
                    } else {
                        cardData
                    }
                }
                currentState.copy(weatherData = items)
            }
            val result = getWeatherDataUseCase(params)
            _uiState.update { currentState ->
                val items = currentState.weatherData.map { cardData ->
                    if (cardData.location.id == location.id) {
                        if (result is WeatherResponse.Success) {
                            cardData.copy(
                                location = location,
                                weather = result.weather,
                                isLoading = false
                            )
                        } else {
                            val error = result as WeatherResponse.Error
                            cardData.copy(
                                location = location,
                                weather = null,
                                isLoading = false,
                                error = error.exception.message
                            )
                        }
                    } else {
                        cardData
                    }
                }
                currentState.copy(weatherData = items)
            }
        }
    }

    private fun addLocation(locationName: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val params = AddLocationParams(locationName, latitude, longitude)
            when (val result = addLocationUseCase(params)) {
                is LocationResponse.Success -> {
                    val cardData = CardData(result.location, null, isLoading = true)
                    val updatedList = _uiState.value.weatherData + cardData
                    _uiState.value = _uiState.value.copy(weatherData = updatedList)
                    handleEvent(HomeEvent.GetWeatherEvent(result.location))
                }

                is LocationResponse.Error -> {
                    _snackBarChannel.send(result.exception.message.toString())
                }
            }
        }
    }

    private fun removeLocation(cardData: CardData) {
        viewModelScope.launch {
            val params = RemoveLocationParams(cardData.location)
            when (val result = removeLocationUseCase(params)) {
                is LocationResponse.Success -> {
                    val updatedList = _uiState.value.weatherData.toMutableList().apply {
                        remove(cardData)
                    }
                    _uiState.value = _uiState.value.copy(weatherData = updatedList)
                }

                is LocationResponse.Error -> {
                    _snackBarChannel.send(result.exception.message.toString())
                }
            }
        }
    }
}