package com.chalupin.practice.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalupin.practice.domain.entity.UserLocation
import com.chalupin.practice.domain.usecase.AddLocationUseCase
import com.chalupin.practice.domain.usecase.GetLocationsUseCase
import com.chalupin.practice.domain.usecase.GetWeatherDataUseCase
import com.chalupin.practice.domain.usecase.RemoveLocationUseCase
import com.chalupin.practice.domain.usecase.params.AddLocationParams
import com.chalupin.practice.domain.usecase.params.GetLocationsParams
import com.chalupin.practice.domain.usecase.params.GetWeatherDataParams
import com.chalupin.practice.domain.usecase.params.RemoveLocationParams
import com.chalupin.practice.domain.util.LocationResponse
import com.chalupin.practice.domain.util.WeatherResponse
import com.chalupin.practice.presentation.home.util.CardData
import com.chalupin.practice.presentation.home.util.HomeEvent
import com.chalupin.practice.presentation.home.util.HomeState
import com.chalupin.practice.presentation.home.util.PermissionChecker
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
    private val removeLocationUseCase: RemoveLocationUseCase,
    private val permissionChecker: PermissionChecker,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState(weatherCardData = mutableListOf()))
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private val _snackBarChannel = Channel<String>(Channel.BUFFERED)
    val snackBarFlow = _snackBarChannel.receiveAsFlow()

    private val _hasLocationPermission = MutableStateFlow(permissionChecker.hasLocationPermission())
    val hasLocationPermission: StateFlow<Boolean> = _hasLocationPermission

    init {
        loadLocations()
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.AllowLocationPermissionEvent -> {
                _hasLocationPermission.value = true
                handleEvent(HomeEvent.LoadLocationsEvent)
            }

            HomeEvent.LoadLocationsEvent -> loadLocations()
            is HomeEvent.GetWeatherEvent -> getWeatherData(event.userLocation)
            is HomeEvent.AddLocationEvent -> addLocation(
                event.locationName, event.latitude, event.longitude
            )

            is HomeEvent.RemoveLocationEvent -> removeLocation(event.cardData)
        }
    }

    private fun loadLocations() {
        viewModelScope.launch {
            val params = GetLocationsParams(permissionChecker.hasLocationPermission())
            when (val result = getLocationsUseCase(params)) {
                is LocationResponse.Success -> {
                    val locations = result.location
                    _uiState.update { currentState ->
                        val items = locations.map { location ->
                            CardData(
                                location, null, isLoading = true
                            )
                        }
                        currentState.copy(weatherCardData = items)
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

    private fun getWeatherData(userLocation: UserLocation) {
        viewModelScope.launch {
            val latitude = userLocation.latitude
            val longitude = userLocation.longitude
            _uiState.update { currentState ->
                val items = currentState.weatherCardData.map { cardData ->
                    if (cardData.userLocation.id == userLocation.id) {
                        cardData.copy(isLoading = true)
                    } else {
                        cardData
                    }
                }
                currentState.copy(weatherCardData = items)
            }
            val params = GetWeatherDataParams(latitude, longitude)
            val result = getWeatherDataUseCase(params)
            _uiState.update { currentState ->
                val items = currentState.weatherCardData.map { cardData ->
                    if (cardData.userLocation.id == userLocation.id) {
                        if (result is WeatherResponse.Success) {
                            cardData.copy(
                                userLocation = userLocation,
                                weather = result.weather,
                                isLoading = false
                            )
                        } else {
                            val error = result as WeatherResponse.Error
                            _snackBarChannel.send(result.exception.message.toString())
                            cardData.copy(
                                userLocation = userLocation,
                                weather = null,
                                isLoading = false,
                                error = error.exception.message
                            )
                        }
                    } else {
                        cardData
                    }
                }
                currentState.copy(weatherCardData = items)
            }
        }
    }

    private fun addLocation(locationName: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val params = AddLocationParams(locationName, latitude, longitude)
            when (val result = addLocationUseCase(params)) {
                is LocationResponse.Success -> {
                    val cardData = CardData(
                        result.location,
                        null,
                        isLoading = true
                    )
                    val updatedList = _uiState.value.weatherCardData + cardData
                    _uiState.value = _uiState.value.copy(weatherCardData = updatedList)
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
            val params = RemoveLocationParams(cardData.userLocation)
            when (val result = removeLocationUseCase(params)) {
                is LocationResponse.Success -> {
                    val updatedList = _uiState.value.weatherCardData.toMutableList().apply {
                        remove(cardData)
                    }
                    _uiState.value = _uiState.value.copy(weatherCardData = updatedList)
                }

                is LocationResponse.Error -> {
                    _snackBarChannel.send(result.exception.message.toString())
                }
            }
        }
    }
}