package com.chalupin.weather.data.mapper

import com.chalupin.weather.data.dto.CurrentDto
import com.chalupin.weather.data.dto.CurrentUnitsDto
import com.chalupin.weather.data.dto.DailyDto
import com.chalupin.weather.data.dto.WeatherDto
import com.chalupin.weather.domain.entity.CurrentEntity
import com.chalupin.weather.domain.entity.CurrentUnitsEntity
import com.chalupin.weather.domain.entity.DailyEntity
import com.chalupin.weather.domain.entity.WeatherEntity


fun WeatherDto.toDomain(): WeatherEntity {
    return WeatherEntity(
        currentEntity = this.currentDto.toDomain(),
        currentUnitsEntity = this.currentUnitsDto.toDomain(),
        dailyEntity = this.dailyDto.toDomain()
    )
}

fun CurrentDto.toDomain(): CurrentEntity {
    return CurrentEntity(
        date = this.date,
        temperature = this.temperature,
        weatherCode = this.weatherCode
    )
}

fun CurrentUnitsDto.toDomain(): CurrentUnitsEntity {
    return CurrentUnitsEntity(
        temperatureUnit = this.temperatureUnit,
    )
}

fun DailyDto.toDomain(): DailyEntity {
    return DailyEntity(
        date = this.date,
        temperatureMin = this.temperatureMin,
        temperatureMax = this.temperatureMax,
        weatherCode = this.weatherCode
    )
}