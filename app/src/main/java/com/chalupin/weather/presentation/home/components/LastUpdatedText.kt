package com.chalupin.weather.presentation.home.components

import android.text.format.DateUtils
import androidx.compose.animation.Crossfade
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun LastUpdatedText() {
    val startTimeMillis = remember { System.currentTimeMillis() }
    var elapsedTimeString by remember {
        mutableStateOf(
            DateUtils.getRelativeTimeSpanString(startTimeMillis).toString()
        )
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            elapsedTimeString = DateUtils.getRelativeTimeSpanString(startTimeMillis).toString()
            delay(1000L)
        }
    }

    Crossfade(targetState = elapsedTimeString, label = "elapsed time crossfade") { text ->
        Text(
            text,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
        )
    }
}