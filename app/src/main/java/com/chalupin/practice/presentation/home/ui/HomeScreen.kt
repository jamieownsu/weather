package com.chalupin.practice.presentation.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.chalupin.practice.R
import com.chalupin.practice.presentation.home.components.LocationPermissionCard
import com.chalupin.practice.presentation.home.components.OptionsBottomSheet
import com.chalupin.practice.presentation.home.components.WeatherCard
import com.chalupin.practice.presentation.home.components.WeatherCardHeader
import com.chalupin.practice.presentation.home.util.HomeEvent
import com.chalupin.practice.presentation.home.util.ModalState
import com.chalupin.practice.presentation.home.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val locationPermissionState by viewModel.hasLocationPermission.collectAsStateWithLifecycle()

    val snackBarMessageState = viewModel.snackBarFlow.collectAsState(initial = null)

    var modalState by remember { mutableStateOf<ModalState>(ModalState.Hidden) }

    LaunchedEffect(snackBarMessageState.value) {
        val message = snackBarMessageState.value
        if (message != null) {
            snackBarHostState.showSnackbar(message)
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
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
//                    navController.navigate(NavRoutes.SEARCH_SCREEN)
                    viewModel.handleEvent(
                        HomeEvent.AddLocationEvent(
                            "New York", 40.7143, -74.006

                        )
                    )
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (!locationPermissionState) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
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
            LazyColumn {
                items(uiState.weatherCardData) { data ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        WeatherCardHeader(
                            data.userLocation.locationName,
                            showDelete = data.userLocation.id != -1L,
                            onRemoveClick = {
                                modalState = ModalState.ShowDeleteConfirmation(data)
                            })
                        WeatherCard(
                            data.weather,
                            data.isLoading,
                            onRefreshClick = {
                                viewModel.handleEvent(
                                    HomeEvent.GetWeatherEvent(data.userLocation)
                                )
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
                            HomeEvent.RemoveLocationEvent(state.cardData)
                        )
                        modalState = ModalState.Hidden
                    }
                )
            }
        }

        ModalState.Hidden -> {}
    }
}