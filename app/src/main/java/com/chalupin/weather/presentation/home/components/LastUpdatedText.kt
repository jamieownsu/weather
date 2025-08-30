package com.chalupin.weather.presentation.home.components

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.chalupin.weather.R
import kotlinx.coroutines.delay

@Composable
fun LastUpdatedText() {
    val startTimeMillis = remember { System.currentTimeMillis() }
    var elapsedMinutes by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = true) {
        while (true) {
            val now = System.currentTimeMillis()
            val diff = now - startTimeMillis
            val seconds = diff / 1000
            val minutes = seconds / 60
            elapsedMinutes = minutes.toInt()
            Log.d("elapsed", elapsedMinutes.toString())
            delay(1000L)
        }
    }

    Text(
        if (elapsedMinutes != 0) {
            pluralStringResource(
                id = R.plurals.last_updated,
                elapsedMinutes,
                elapsedMinutes
            )
        } else {
            stringResource(R.string.updated_just_now)
        },
        style = MaterialTheme.typography.labelSmall,
        color = Color.Gray,
    )
}