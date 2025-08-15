package com.chalupin.practice.presentation.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.chalupin.practice.core.navigation.NavRoutes
import com.chalupin.practice.domain.entity.Location
import com.chalupin.practice.presentation.home.components.WeatherCard
import com.chalupin.practice.presentation.home.util.HomeEvent
import com.chalupin.practice.presentation.home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.snackBarFlow.collectLatest { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("uWeather") },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    navController.navigate(NavRoutes.SEARCH_SCREEN)
//                    viewModel.handleEvent(
//                        HomeEvent.AddLocationEvent(
//                            "ttttttt", 40.7143, -74.006
//
//                        )
//                    )
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }

        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            items(uiState.weatherData) { data ->
                WeatherCard(data.weather, data.isLoading, onRemoveClick = {
                    viewModel.handleEvent(
                        HomeEvent.RemoveLocationEvent(data)
                    )
                })
            }
        }
    }
}