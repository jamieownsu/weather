package com.chalupin.weather.data.mapper

import com.chalupin.weather.data.dto.CurrentModel
import com.chalupin.weather.data.dto.CurrentUnitsModel
import com.chalupin.weather.data.dto.DailyModel
import com.chalupin.weather.data.dto.WeatherModel
import com.chalupin.weather.domain.entity.Current
import com.chalupin.weather.domain.entity.CurrentUnits
import com.chalupin.weather.domain.entity.Daily
import com.chalupin.weather.domain.entity.Weather


fun WeatherModel.toDomain(): Weather {
    return Weather(
        current = this.currentModel.toDomain(),
        currentUnits = this.currentUnitsModel.toDomain(),
        daily = this.dailyModel.toDomain()
    )
}

fun CurrentModel.toDomain(): Current {
    return Current(
        date = this.date,
        temperature = this.temperature,
        weatherCode = this.weatherCode
    )
}

fun CurrentUnitsModel.toDomain(): CurrentUnits {
    return CurrentUnits(
        temperatureUnit = this.temperatureUnit,
    )
}

fun DailyModel.toDomain(): Daily {
    return Daily(
        date = this.date,
        temperatureMin = this.temperatureMin,
        temperatureMax = this.temperatureMax,
        weatherCode = this.weatherCode
    )
}