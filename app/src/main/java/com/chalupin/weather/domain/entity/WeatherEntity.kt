package com.chalupin.weather.domain.entity

import com.chalupin.weather.domain.enum.WeatherCodes
import com.chalupin.weather.presentation.home.util.DailyColumnData
import com.chalupin.weather.presentation.home.util.WeatherImageType
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

    fun getCurrentWeatherTypeIcon(): WeatherImageType {
        return WeatherImageType.WeatherImageAnimated(WeatherCodes.getLottieIconFile(currentEntity.weatherCode))
    }

    fun getDailyData(): List<DailyColumnData> {
        val dayOfWeekFormat = DateTimeFormatter.ofPattern("EEE")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val retList = mutableListOf<DailyColumnData>()
        for ((i, item) in dailyEntity.date.withIndex()) {
            val date = LocalDate.parse(item, formatter)
            retList.add(
                DailyColumnData(
                    date.format(dayOfWeekFormat),
                    "${dailyEntity.temperatureMax[i].roundToInt()}${currentUnitsEntity.temperatureUnit}",
                    "${dailyEntity.temperatureMin[i].roundToInt()}${currentUnitsEntity.temperatureUnit}",
                    WeatherImageType.WeatherImageStatic(WeatherCodes.getSvgFile(dailyEntity.weatherCode[i]))
                )
            )
        }
        return retList
    }
}