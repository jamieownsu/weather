package com.chalupin.weather.domain.entity

import com.chalupin.weather.domain.enum.WeatherCodes
import com.chalupin.weather.presentation.home.util.DailyColumn
import com.chalupin.weather.presentation.home.util.WeatherIconType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data class WeatherEntity(val currentEntity: CurrentEntity, val currentUnitsEntity: CurrentUnitsEntity, val dailyEntity: DailyEntity) {
    fun getTemperature(): String {
        return "${currentEntity.temperature.roundToInt()}${currentUnitsEntity.temperatureUnit}"
    }

    fun getCurrentWeatherType(): String {
        return WeatherCodes.getDescriptionForCode(currentEntity.weatherCode)
    }

    fun getCurrentWeatherTypeIcon(): WeatherIconType {
        return WeatherIconType.WeatherIconAnimated(WeatherCodes.getLottieIconFile(currentEntity.weatherCode))
    }

    fun getDailyData(): List<DailyColumn> {
        val dayOfWeekFormat = DateTimeFormatter.ofPattern("EEE")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val retList = mutableListOf<DailyColumn>()
        for ((i, item) in dailyEntity.date.withIndex()) {
            val date = LocalDate.parse(item, formatter)
            retList.add(
                DailyColumn(
                    date.format(dayOfWeekFormat),
                    "${dailyEntity.temperatureMax[i].roundToInt()}${currentUnitsEntity.temperatureUnit}",
                    "${dailyEntity.temperatureMin[i].roundToInt()}${currentUnitsEntity.temperatureUnit}",
                    WeatherIconType.WeatherIconStatic(WeatherCodes.getSvgFile(dailyEntity.weatherCode[i]))
                )
            )
        }
        return retList
    }
}