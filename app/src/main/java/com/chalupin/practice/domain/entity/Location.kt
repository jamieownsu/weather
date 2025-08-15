package com.chalupin.practice.domain.entity

data class Location(
    val id: Long,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
)