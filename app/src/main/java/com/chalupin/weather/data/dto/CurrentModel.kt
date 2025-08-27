package com.chalupin.weather.data.dto

import com.google.gson.annotations.SerializedName

data class CurrentModel(
    @SerializedName("time")
    val date: String,
    @SerializedName("temperature_2m")
    val temperature: Double,
    @SerializedName("weather_code")
    val weatherCode: Int
)
