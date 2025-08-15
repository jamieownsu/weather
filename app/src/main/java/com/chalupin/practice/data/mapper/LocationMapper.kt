package com.chalupin.practice.data.mapper

import com.chalupin.practice.data.database.entity.LocationEntity
import com.chalupin.practice.domain.entity.Location

fun LocationEntity.toDomain(): Location {
    return Location(
        id = this.id,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}

fun Location.toEntity(): LocationEntity {
    return LocationEntity(
        id = this.id,
        locationName = this.locationName,
        latitude = this.latitude,
        longitude = this.longitude
    )
}