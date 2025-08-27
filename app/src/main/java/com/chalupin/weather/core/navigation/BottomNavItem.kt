package com.chalupin.weather.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem(NavRoutes.HOME_SCREEN, "Home", Icons.Default.Home)
    object Profile : BottomNavItem(NavRoutes.SEARCH_SCREEN, "Search", Icons.Default.Search)
}