package com.chalupin.weather.data.mapper

import com.chalupin.weather.data.dto.LocationDto
import com.chalupin.weather.domain.entity.LocationEntity

fun LocationDto.toDomain(): LocationEntity {
    return LocationEntity(
        id = this.id,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}

fun LocationEntity.toEntity(): LocationDto {
    return LocationDto(
        id = this.id,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude
    )
}