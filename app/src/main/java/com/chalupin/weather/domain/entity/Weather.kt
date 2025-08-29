package com.chalupin.weather.domain.entity

import androidx.annotation.RawRes
import com.chalupin.weather.domain.enum.WeatherCodes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data class Weather(val current: Current, val currentUnits: CurrentUnits, val daily: Daily) {
    fun getTemperature(): String {
        return "${current.temperature.roundToInt()}${currentUnits.temperatureUnit}"
    }

//    fun getDate(): String {
//        val date = LocalDateTime.parse(current.date)
//        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
//            .withLocale(Locale.getDefault())
//        val formattedDate: String = date.format(formatter)
//        return formattedDate
//    }

    fun getCurrentWeatherType(): String {
        return WeatherCodes.getDescriptionForCode(current.weatherCode)
    }

    @RawRes
    fun getCurrentWeatherTypeIcon(): Int {
        return WeatherCodes.getLottieIconFile(current.weatherCode)
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
                    "${daily.temperatureMin[i].roundToInt()}${currentUnits.temperatureUnit}",
                    "${daily.temperatureMin[i].roundToInt()}${currentUnits.temperatureUnit}",
                    WeatherCodes.getLottieIconFile(daily.weatherCode[i]),
                    WeatherCodes.getSvgFile(daily.weatherCode[i])
                )
            )
        }
        return retList
    }
}