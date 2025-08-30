package com.chalupin.weather.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chalupin.weather.R
import com.chalupin.weather.domain.entity.WeatherEntity

@Composable
fun WeatherCard(
    weatherEntity: WeatherEntity?,
    isLoading: Boolean,
    onRefreshClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .height(210.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            weatherEntity?.let {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = it.getTemperature(),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(Modifier.width(8.dp))
                        WeatherTypeImage(
                            it.getCurrentWeatherTypeIcon(),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = it.getCurrentWeatherType(),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = onRefreshClick,
//                            colors = IconButtonDefaults.iconButtonColors(
//                                containerColor = MaterialTheme.colorScheme.onSecondary
//                            )
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items(it.getDailyData()) { item ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = item.dayOfWeek,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = item.maxTemp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                WeatherTypeImage(
                                    item.weatherImageType,
                                )
                                Text(
                                    text = item.minTemp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                    LastUpdatedText()
                    }
                    Spacer(Modifier.height(2.dp))
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(id = R.string.data_unavailable))
                }
            }
        }
    }
}