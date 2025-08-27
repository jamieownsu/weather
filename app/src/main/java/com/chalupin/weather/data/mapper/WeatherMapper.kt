package com.chalupin.weather.data.mapper

import com.chalupin.weather.data.dto.CurrentDto
import com.chalupin.weather.data.dto.CurrentUnitsDto
import com.chalupin.weather.data.dto.DailyDto
import com.chalupin.weather.data.dto.WeatherDto
import com.chalupin.weather.domain.entity.Current
import com.chalupin.weather.domain.entity.CurrentUnits
import com.chalupin.weather.domain.entity.Daily
import com.chalupin.weather.domain.entity.Weather


fun WeatherDto.toDomain(): Weather {
    return Weather(
        current = this.currentDto.toDomain(),
        currentUnits = this.currentUnitsDto.toDomain(),
        daily = this.dailyDto.toDomain()
    )
}

fun CurrentDto.toDomain(): Current {
    return Current(
        date = this.date,
        temperature = this.temperature,
        weatherCode = this.weatherCode
    )
}

fun CurrentUnitsDto.toDomain(): CurrentUnits {
    return CurrentUnits(
        temperatureUnit = this.temperatureUnit,
    )
}

fun DailyDto.toDomain(): Daily {
    return Daily(
        date = this.date,
        temperatureMin = this.temperatureMin,
        temperatureMax = this.temperatureMax,
        weatherCode = this.weatherCode
    )
}