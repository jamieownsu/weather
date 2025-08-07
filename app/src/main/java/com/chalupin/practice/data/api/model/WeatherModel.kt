package com.chalupin.practice.data.api.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class WeatherModel(
    @SerializedName("date")
    val date: Date,
)