import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chalupin.practice.core.navigation.NavRoutes
import com.chalupin.practice.presentation.favourites.ui.FavouritesScreen
import com.chalupin.practice.presentation.home.ui.HomeScreen


@Composable
fun AppNavigationHost(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME_SCREEN,
    ) {
        composable(NavRoutes.HOME_SCREEN) {
            HomeScreen(
                innerPadding
//                viewModel = hiltViewModel(),
            )
        }
        composable(
            NavRoutes.FAVORITES_SCREEN
        ) {
            FavouritesScreen(
                innerPadding
//                viewModel = hiltViewModel(),
            )
        }
    }
}


