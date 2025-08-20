package com.chalupin.practice.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherCardHeader(locationName: String, showDelete: Boolean, onRemoveClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            locationName,
            modifier = Modifier.padding(bottom = if (showDelete) 0.dp else 12.dp),
            style = MaterialTheme.typography.titleLarge
        )
        if (showDelete) {
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onRemoveClick) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    }
}