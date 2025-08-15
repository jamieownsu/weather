package com.chalupin.practice.domain.entity

data class Daily(
    val date: List<String> = emptyList(),
    val temperatureMin: List<Double> = emptyList(),
    val temperatureMax: List<Double> = emptyList(),
    val weatherCode: List<Int> = emptyList()
)