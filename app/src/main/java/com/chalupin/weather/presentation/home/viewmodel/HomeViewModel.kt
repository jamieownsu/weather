package com.chalupin.weather.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalupin.weather.domain.entity.LocationEntity
import com.chalupin.weather.domain.usecase.AddLocationUseCase
import com.chalupin.weather.domain.usecase.GetLocationsUseCase
import com.chalupin.weather.domain.usecase.GetUserLocationUseCase
import com.chalupin.weather.domain.usecase.GetWeatherDataUseCase
import com.chalupin.weather.domain.usecase.RemoveLocationUseCase
import com.chalupin.weather.domain.usecase.params.AddLocationParams
import com.chalupin.weather.domain.usecase.params.GetUserLocationParams
import com.chalupin.weather.domain.usecase.params.GetWeatherDataParams
import com.chalupin.weather.domain.usecase.params.RemoveLocationParams
import com.chalupin.weather.domain.util.LocationResponse
import com.chalupin.weather.domain.util.WeatherResponse
import com.chalupin.weather.presentation.home.state.HomeEvent
import com.chalupin.weather.presentation.home.state.HomeState
import com.chalupin.weather.presentation.home.util.PermissionChecker
import com.chalupin.weather.presentation.home.util.WeatherCardData
import com.google.android.gms.tasks.CancellationTokenSource
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
    private val permissionChecker: PermissionChecker,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState(weatherCards = mutableListOf()))
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private val _snackBarChannel = Channel<String>(Channel.BUFFERED)
    val snackBarFlow = _snackBarChannel.receiveAsFlow()

    private val _hasLocationPermission = MutableStateFlow(permissionChecker.hasLocationPermission())
    val hasLocationPermission: StateFlow<Boolean> = _hasLocationPermission

    init {
        loadUserLocation()
        loadLocations()
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.AllowLocationPermissionEvent -> {
                _hasLocationPermission.value = true
                loadUserLocation()
            }

            HomeEvent.LoadUserLocationEvent -> loadUserLocation()
            HomeEvent.LoadLocationsEvent -> loadLocations()
            is HomeEvent.GetWeatherEvent -> getWeatherData(event.locationEntity)
            is HomeEvent.AddLocationEvent -> addLocation(
                event.locationName, event.latitude, event.longitude
            )

            is HomeEvent.RemoveLocationEvent -> removeLocation(event.cardDataState)
        }
    }

    private var cancellationTokenSource: CancellationTokenSource? = null

    fun cancelLocationRequest() {
        cancellationTokenSource?.cancel()
        cancellationTokenSource = null
    }

    private fun loadUserLocation() {
        if (permissionChecker.hasLocationPermission()) {
            viewModelScope.launch {
                try {
                    _uiState.update { currentState ->
                        val items = currentState.weatherCards.toMutableList()
                        val index = items.indexOfFirst { it.locationEntity.id == -1L }
                        if (index != -1) {
                            items[index] = items[index].copy(
                                isLoading = true
                            )
                            currentState.copy(weatherCards = items)
                        } else {
                            val localWeather = LocationEntity.createLocalWeatherLocation()
                            val cardDataState = WeatherCardData(
                                localWeather, null, isLoading = true
                            )
                            val updatedList = listOf(cardDataState) + _uiState.value.weatherCards
                            currentState.copy(weatherCards = updatedList)
                        }
                    }
                    cancellationTokenSource = CancellationTokenSource()
                    val params = GetUserLocationParams(cancellationTokenSource!!.token)
                    when (val result = getUserLocationUseCase(params)) {
                        is LocationResponse.Success -> {
                            val location = result.location
                            _uiState.update { currentState ->
                                val items = currentState.weatherCards.toMutableList()
                                val index = items.indexOfFirst { it.locationEntity.id == location.id }
                                if (index != -1) {
                                    items[index] = items[index].copy(
                                        locationEntity = location,
                                        isLoading = true
                                    )
                                }
                                currentState.copy(weatherCards = items)
                            }
                            handleEvent(HomeEvent.GetWeatherEvent(location))
                        }

                        is LocationResponse.Error -> {
                            _snackBarChannel.send(result.exception.message.toString())
                        }
                    }
                } finally {
                    cancellationTokenSource = null
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

    private fun getWeatherData(locationEntity: LocationEntity) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val items = currentState.weatherCards.toMutableList()
                val index = items.indexOfFirst { it.locationEntity.id == locationEntity.id }
                if (index != -1) {
                    items[index] = items[index].copy(
                        isLoading = true
                    )
                }
                currentState.copy(weatherCards = items)
            }
            val latitude = locationEntity.latitude
            val longitude = locationEntity.longitude
            val params = GetWeatherDataParams(latitude, longitude)
            val result = getWeatherDataUseCase(params)
            _uiState.update { currentState ->
                val items = currentState.weatherCards.toMutableList()
                val index = items.indexOfFirst { it.locationEntity.id == locationEntity.id }
                if (index != -1) {
                    if (result is WeatherResponse.Success) {
                        items[index] = items[index].copy(
                            weatherEntity = result.weather,
                            isLoading = false
                        )
                    } else {
                        val error = result as WeatherResponse.Error
                        _snackBarChannel.send(result.exception.message.toString())
                        items[index] = items[index].copy(
                            isLoading = false,
                            error = error.exception.message
                        )
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
            val params = RemoveLocationParams(cardDataState.locationEntity)
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