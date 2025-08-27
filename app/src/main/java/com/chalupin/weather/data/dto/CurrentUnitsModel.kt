package com.chalupin.weather.data.dto

import com.google.gson.annotations.SerializedName

data class CurrentUnitsModel(
    @SerializedName("temperature_2m")
    val temperatureUnit: String,
)
