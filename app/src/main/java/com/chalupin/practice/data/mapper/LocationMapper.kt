package com.chalupin.practice.data.mapper

import com.chalupin.practice.data.database.entity.LocationEntity
import com.chalupin.practice.domain.entity.UserLocation

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