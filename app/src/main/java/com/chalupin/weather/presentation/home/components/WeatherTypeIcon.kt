package com.chalupin.weather.presentation.home.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun WeatherTypeIcon(code: Int) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(code)
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 2f,
        modifier = Modifier.size(48.dp)
    )
}