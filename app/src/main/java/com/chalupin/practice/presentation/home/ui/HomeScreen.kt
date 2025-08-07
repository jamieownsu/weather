package com.chalupin.practice.presentation.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chalupin.practice.presentation.home.util.HomeEvent
import com.chalupin.practice.presentation.home.util.HomeState
import com.chalupin.practice.presentation.home.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(innerPadding: PaddingValues, viewModel: HomeViewModel = hiltViewModel()) {

    val pullToRefreshState = rememberPullToRefreshState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = uiState is HomeState.Loading,
        onRefresh = { viewModel.handleEvent(HomeEvent.LoadWeatherEvent) },
        state = pullToRefreshState,
        content = {
            when (uiState) {
                is HomeState.Loading -> LoadingScreen(innerPadding)
                is HomeState.Success -> {
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
//            .padding(horizontal = 16.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Card Title",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = "This is a simple example of a Jetpack Compose Card. " +
                                            "It contains a title and some body text.",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                is HomeState.Error -> ErrorScreen(innerPadding)
            }
        })

}