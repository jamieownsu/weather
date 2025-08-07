package com.chalupin.practice.presentation.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.chalupin.practice.core.navigation.NavRoutes

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem(NavRoutes.HOME_SCREEN, "Home", Icons.Default.Home)
    object Profile : BottomNavItem(NavRoutes.FAVORITES_SCREEN, "Favourites", Icons.Default.Person)
}