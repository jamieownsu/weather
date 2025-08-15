package com.chalupin.practice.data.mapper

import com.chalupin.practice.data.api.model.CurrentModel
import com.chalupin.practice.data.api.model.CurrentUnitsModel
import com.chalupin.practice.data.api.model.DailyModel
import com.chalupin.practice.data.api.model.WeatherModel
import com.chalupin.practice.domain.entity.Current
import com.chalupin.practice.domain.entity.CurrentUnits
import com.chalupin.practice.domain.entity.Daily
import com.chalupin.practice.domain.entity.Weather


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