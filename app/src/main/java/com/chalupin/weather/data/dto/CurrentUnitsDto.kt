package com.chalupin.weather.data.dto

import com.google.gson.annotations.SerializedName

data class CurrentUnitsDto(
    @SerializedName("temperature_2m")
    val temperatureUnit: String,
)
