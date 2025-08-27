package com.chalupin.weather.data.mapper

import com.chalupin.weather.data.dto.LocationEntity
import com.chalupin.weather.domain.entity.UserLocation

fun LocationEntity.toDomain(): UserLocation {
    return UserLocation(
        id = this.id,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}

fun UserLocation.toEntity(): LocationEntity {
    return LocationEntity(
        id = this.id,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude
    )
}