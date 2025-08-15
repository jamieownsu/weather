package com.chalupin.practice.data.api.model

import com.google.gson.annotations.SerializedName

class DailyModel {
    @SerializedName("time")
    val date: List<String> = emptyList()

    @SerializedName("temperature_2m_min")
    val temperatureMin: List<Double> = emptyList()

    @SerializedName("temperature_2m_max")
    val temperatureMax: List<Double> = emptyList()

    @SerializedName("weather_code")
    val weatherCode: List<Int> = emptyList()
}