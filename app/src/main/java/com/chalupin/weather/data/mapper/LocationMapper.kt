package com.chalupin.weather.data.mapper

import com.chalupin.weather.data.dto.LocationDto
import com.chalupin.weather.domain.entity.UserLocation

fun LocationDto.toDomain(): UserLocation {
    return UserLocation(
        id = this.id,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}

fun UserLocation.toEntity(): LocationDto {
    return LocationDto(
        id = this.id,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude
    )
}