package com.chalupin.weather.domain.repository

interface RemoteConfigRepository {
    suspend fun fetchAndActivate(): Boolean

    fun getString(key: String): String
}