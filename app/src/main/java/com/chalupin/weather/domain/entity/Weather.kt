package com.chalupin.weather.domain.entity

import com.chalupin.weather.domain.enum.WeatherCodes
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.roundToInt

data class Weather(val current: Current, val currentUnits: CurrentUnits, val daily: Daily) {
    fun getTemperature(): String {
        return "${current.temperature} ${currentUnits.temperatureUnit}"
    }

    fun getDate(): String {
        val date = LocalDateTime.parse(current.date)
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            .withLocale(Locale.getDefault())
        val formattedDate: String = date.format(formatter)
        return formattedDate
    }

    fun getCurrentWeatherType(): String {
        return WeatherCodes.getDescriptionForCode(current.weatherCode)
    }

    fun getDailyDates(): List<String> {
        val dayOfWeekFormat = DateTimeFormatter.ofPattern("EEE")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return daily.date.map {
            val date = LocalDate.parse(it, formatter)
            date.format(dayOfWeekFormat)
        }
    }

    fun getDailyMinTemps(): List<String> {
        return daily.temperatureMin.map { "${it.roundToInt()}${currentUnits.temperatureUnit}" }
    }

    fun getDailyMaxTemps(): List<String> {
        return daily.temperatureMax.map { "${it.roundToInt()}${currentUnits.temperatureUnit}" }
    }
}