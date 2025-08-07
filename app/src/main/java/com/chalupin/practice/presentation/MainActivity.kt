package com.chalupin.practice.presentation

import AppNavigationHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chalupin.practice.core.theme.PracticeTheme
import com.chalupin.practice.presentation.shared.BottomNavBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("My App") },
                        )
                    },
                    bottomBar = { BottomNavBar(navController) }
                ) { innerPadding ->
                    AppNavigationHost(navController = navController, innerPadding)
                }
            }
        }
    }
}