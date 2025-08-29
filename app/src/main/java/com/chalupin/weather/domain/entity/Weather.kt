package com.chalupin.weather.domain.entity

import com.chalupin.weather.domain.enum.WeatherCodes
import com.chalupin.weather.presentation.home.util.WeatherIconType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data class Weather(val current: Current, val currentUnits: CurrentUnits, val daily: Daily) {
    fun getTemperature(): String {
        return "${current.temperature.roundToInt()}${currentUnits.temperatureUnit}"
    }

    fun getCurrentWeatherType(): String {
        return WeatherCodes.getDescriptionForCode(current.weatherCode)
    }

    fun getCurrentWeatherTypeIcon(): WeatherIconType {
        return WeatherIconType.WeatherIconAnimated(WeatherCodes.getLottieIconFile(current.weatherCode))
    }

    fun getDailyData(): List<DailyColumn> {
        val dayOfWeekFormat = DateTimeFormatter.ofPattern("EEE")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val retList = mutableListOf<DailyColumn>()
        for ((i, item) in daily.date.withIndex()) {
            val date = LocalDate.parse(item, formatter)
            retList.add(
                DailyColumn(
                    date.format(dayOfWeekFormat),
                    "${daily.temperatureMax[i].roundToInt()}${currentUnits.temperatureUnit}",
                    "${daily.temperatureMin[i].roundToInt()}${currentUnits.temperatureUnit}",
                    WeatherIconType.WeatherIconStatic(WeatherCodes.getSvgFile(daily.weatherCode[i]))
                )
            )
        }
        return retList
    }
}