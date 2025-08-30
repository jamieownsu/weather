package com.chalupin.weather.presentation.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chalupin.weather.R
import com.chalupin.weather.presentation.home.components.AddLocationButton
import com.chalupin.weather.presentation.home.components.LocationPermissionCard
import com.chalupin.weather.presentation.home.components.OptionsBottomSheet
import com.chalupin.weather.presentation.home.components.WeatherCard
import com.chalupin.weather.presentation.home.components.WeatherCardHeader
import com.chalupin.weather.presentation.home.state.HomeEvent
import com.chalupin.weather.presentation.home.state.ModalState
import com.chalupin.weather.presentation.home.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val locationPermissionState by viewModel.hasLocationPermission.collectAsStateWithLifecycle()
    val snackBarMessageState = viewModel.snackBarFlow.collectAsState(initial = null)
    var modalState by remember { mutableStateOf<ModalState>(ModalState.Hidden) }
    var hasLaunched by remember { mutableStateOf(false) }

    LaunchedEffect(snackBarMessageState.value) {
        val message = snackBarMessageState.value
        if (message != null) {
            snackBarHostState.showSnackbar(message)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        if (hasLaunched) {
            viewModel.handleEvent(
                HomeEvent.LoadUserLocationEvent
            )
        } else {
            hasLaunched = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelLocationRequest()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            AddLocationButton(
                fabClicked = { hasLaunched = false },
                onLocationAdded = { place ->
                    val placeName = place.formattedAddress ?: "Unknown"
                    place.location?.let {
                        val latitude = it.latitude
                        val longitude = it.longitude
                        viewModel.handleEvent(
                            HomeEvent.AddLocationEvent(
                                placeName, latitude, longitude
                            )
                        )
                    }
                })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (!locationPermissionState) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    WeatherCardHeader(
                        "Local weather",
                        showDelete = false,
                        onRemoveClick = {})
                    LocationPermissionCard(onAllowLocationPermission = {
                        viewModel.handleEvent(
                            HomeEvent.AllowLocationPermissionEvent
                        )
                    })
                }
            }
            LazyColumn(contentPadding = PaddingValues(bottom = 72.dp)) {
                items(uiState.weatherCards) { data ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        WeatherCardHeader(
                            data.locationEntity.locationName,
                            showDelete = data.locationEntity.id != -1L,
                            onRemoveClick = {
                                modalState = ModalState.ShowDeleteConfirmation(data)
                            })
                        WeatherCard(
                            data.weatherEntity,
                            data.isLoading,
                            onRefreshClick = {
                                if (data.locationEntity.id == -1L) {
                                    viewModel.handleEvent(
                                        HomeEvent.LoadUserLocationEvent
                                    )
                                } else {
                                    viewModel.handleEvent(
                                        HomeEvent.GetWeatherEvent(data.locationEntity)
                                    )
                                }
                            })
                    }
                }
            }
        }
    }
    when (val state = modalState) {
        is ModalState.ShowDeleteConfirmation -> {
            val sheetState = rememberModalBottomSheetState()
            ModalBottomSheet(
                onDismissRequest = {
                    modalState = ModalState.Hidden
                },
                sheetState = sheetState
            ) {
                OptionsBottomSheet(
                    onDeleteClicked = {
                        viewModel.handleEvent(
                            HomeEvent.RemoveLocationEvent(state.cardDataState)
                        )
                        modalState = ModalState.Hidden
                    }
                )
            }
        }

        ModalState.Hidden -> {}
    }
}