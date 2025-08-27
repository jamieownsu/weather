package com.chalupin.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class CurrentModel(
    @SerializedName("time")
    val date: String,
    @SerializedName("temperature_2m")
    val temperature: Double,
    @SerializedName("weather_code")
    val weatherCode: Int
)
