package com.chalupin.practice.data.api.model

import com.google.gson.annotations.SerializedName

data class CurrentUnitsModel(
    @SerializedName("temperature_2m")
    val temperatureUnit: String,
)
