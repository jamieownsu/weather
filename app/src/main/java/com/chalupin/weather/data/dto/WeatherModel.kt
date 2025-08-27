package com.chalupin.weather.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("current")
    val currentModel: CurrentModel,
    @SerializedName("current_units")
    val currentUnitsModel: CurrentUnitsModel,
    @SerializedName("daily")
    val dailyModel: DailyModel,
)