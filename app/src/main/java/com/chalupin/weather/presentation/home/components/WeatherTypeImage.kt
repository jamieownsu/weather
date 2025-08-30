package com.chalupin.weather.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chalupin.weather.presentation.home.util.WeatherImageType

@Composable
fun WeatherTypeImage(weatherImageType: WeatherImageType) {
    when (weatherImageType) {
        is WeatherImageType.WeatherImageAnimated -> {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(weatherImageType.res)
            )

            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                speed = 2f,
                modifier = Modifier.size(56.dp)
            )
        }

        is WeatherImageType.WeatherImageStatic -> {
            Image(
                painter = painterResource(id = weatherImageType.res),
                contentDescription = "A weather icon",
                modifier = Modifier.size(32.dp)
            )
        }
    }

}