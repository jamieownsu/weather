package com.chalupin.practice.data.api

import com.chalupin.practice.data.api.model.WeatherModel
import retrofit2.http.GET

interface WeatherService {
    @GET("assignment.json")
    suspend fun getWeather(): WeatherModel
}