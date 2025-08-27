package com.chalupin.weather.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("current")
    val currentDto: CurrentDto,
    @SerializedName("current_units")
    val currentUnitsDto: CurrentUnitsDto,
    @SerializedName("daily")
    val dailyDto: DailyDto,
)