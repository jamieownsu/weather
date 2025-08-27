package com.chalupin.weather.data.repository

import android.util.Log
import com.chalupin.weather.R
import com.chalupin.weather.domain.repository.RemoteConfigRepository
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteConfigRepositoryImpl @Inject constructor() : RemoteConfigRepository {
    private val remoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    override suspend fun fetchAndActivate(): Boolean {
        return try {
            val updated = remoteConfig.fetchAndActivate().await()
            updated
        } catch (e: Exception) {
            Log.e("RemoteConfigRepositoryImpl", e.message.toString())
            false
        }
    }

    override fun getString(key: String): String {
        return remoteConfig.getString(key)
    }
}