package com.chalupin.weather.presentation

import AppNavigationHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chalupin.weather.core.theme.PracticeTheme
import com.chalupin.weather.domain.repository.RemoteConfigRepository
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var remoteConfigService: RemoteConfigRepository

    private fun initializePlacesSdk() {
        CoroutineScope(Dispatchers.IO).launch {
            remoteConfigService.fetchAndActivate()
            val apiKey = remoteConfigService.getString("places_api_key")
            runOnUiThread {
                Places.initializeWithNewPlacesApiEnabled(applicationContext, apiKey)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePlacesSdk()
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                val navController: NavHostController = rememberNavController()
                val snackBarHostState = remember { SnackbarHostState() }
                AppNavigationHost(
                    navController,
                    snackBarHostState,
                )
            }
        }
    }
}