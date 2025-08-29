package com.chalupin.weather.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalupin.weather.domain.entity.UserLocation
import com.chalupin.weather.domain.usecase.AddLocationUseCase
import com.chalupin.weather.domain.usecase.GetLocationsUseCase
import com.chalupin.weather.domain.usecase.GetUserLocationUseCase
import com.chalupin.weather.domain.usecase.GetWeatherDataUseCase
import com.chalupin.weather.domain.usecase.RemoveLocationUseCase
import com.chalupin.weather.domain.usecase.params.AddLocationParams
import com.chalupin.weather.domain.usecase.params.GetWeatherDataParams
import com.chalupin.weather.domain.usecase.params.RemoveLocationParams
import com.chalupin.weather.domain.util.LocationResponse
import com.chalupin.weather.domain.util.WeatherResponse
import com.chalupin.weather.presentation.home.util.WeatherCardData
import com.chalupin.weather.presentation.home.state.HomeEvent
import com.chalupin.weather.presentation.home.state.HomeState
import com.chalupin.weather.presentation.home.util.PermissionChecker
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
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val addLocationUseCase: AddLocationUseCase,
    private val removeLocationUseCase: RemoveLocationUseCase,
    permissionChecker: PermissionChecker,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState(weatherCards = mutableListOf()))
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private val _snackBarChannel = Channel<String>(Channel.BUFFERED)
    val snackBarFlow = _snackBarChannel.receiveAsFlow()

    private val _hasLocationPermission = MutableStateFlow(permissionChecker.hasLocationPermission())
    val hasLocationPermission: StateFlow<Boolean> = _hasLocationPermission

    init {
        if (permissionChecker.hasLocationPermission()) loadUserLocation()
        loadLocations()
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.AllowLocationPermissionEvent -> {
                _hasLocationPermission.value = true
                loadUserLocation()
            }

            HomeEvent.LoadLocationsEvent -> loadLocations()
            is HomeEvent.GetWeatherEvent -> getWeatherData(event.userLocation)
            is HomeEvent.AddLocationEvent -> addLocation(
                event.locationName, event.latitude, event.longitude
            )

            is HomeEvent.RemoveLocationEvent -> removeLocation(event.cardDataState)
        }
    }

    private fun loadUserLocation() {
        viewModelScope.launch {
            val localWeather = UserLocation.createLocalWeatherLocation()
            val cardDataState = WeatherCardData(
                localWeather, null, isLoading = true
            )
            val updatedList = listOf(cardDataState) + _uiState.value.weatherCards
            _uiState.value = _uiState.value.copy(weatherCards = updatedList)
            when (val result = getUserLocationUseCase()) {
                is LocationResponse.Success -> {
                    val location = result.location
                    _uiState.update { currentState ->
                        val items = currentState.weatherCards.map { cardData ->
                            if (cardData.userLocation.id == location.id) {
                                cardData.copy(
                                    userLocation = location,
                                    weather = null,
                                    isLoading = true
                                )
                            } else {
                                cardData
                            }
                        }
                        currentState.copy(weatherCards = items)
                    }
                    handleEvent(HomeEvent.GetWeatherEvent(location))
                }

                is LocationResponse.Error -> {
                    _snackBarChannel.send(result.exception.message.toString())
                }
            }
        }
    }

    private fun loadLocations() {
        viewModelScope.launch {
            when (val result = getLocationsUseCase()) {
                is LocationResponse.Success -> {
                    val locations = result.location
                    val items = locations.map { location ->
                        WeatherCardData(
                            location, null, isLoading = true
                        )
                    }
                    val updatedList = _uiState.value.weatherCards + items
                    _uiState.value = _uiState.value.copy(weatherCards = updatedList)
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
                val items = currentState.weatherCards.map { cardData ->
                    if (cardData.userLocation.id == userLocation.id) {
                        cardData.copy(isLoading = true)
                    } else {
                        cardData
                    }
                }
                currentState.copy(weatherCards = items)
            }
            val params = GetWeatherDataParams(latitude, longitude)
            val result = getWeatherDataUseCase(params)
            _uiState.update { currentState ->
                val items = currentState.weatherCards.map { cardData ->
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
                currentState.copy(weatherCards = items)
            }
        }
    }

    private fun addLocation(locationName: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val params = AddLocationParams(locationName, latitude, longitude)
            when (val result = addLocationUseCase(params)) {
                is LocationResponse.Success -> {
                    val cardDataState = WeatherCardData(
                        result.location,
                        null,
                        isLoading = true
                    )
                    val updatedList = _uiState.value.weatherCards + cardDataState
                    _uiState.value = _uiState.value.copy(weatherCards = updatedList)
                    handleEvent(HomeEvent.GetWeatherEvent(result.location))
                }

                is LocationResponse.Error -> {
                    _snackBarChannel.send(result.exception.message.toString())
                }
            }
        }
    }

    private fun removeLocation(cardDataState: WeatherCardData) {
        viewModelScope.launch {
            val params = RemoveLocationParams(cardDataState.userLocation)
            when (val result = removeLocationUseCase(params)) {
                is LocationResponse.Success -> {
                    val updatedList = _uiState.value.weatherCards.toMutableList().apply {
                        remove(cardDataState)
                    }
                    _uiState.value = _uiState.value.copy(weatherCards = updatedList)
                }

                is LocationResponse.Error -> {
                    _snackBarChannel.send(result.exception.message.toString())
                }
            }
        }
    }
}