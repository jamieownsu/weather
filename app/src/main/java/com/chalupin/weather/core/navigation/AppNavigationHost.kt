import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chalupin.weather.core.navigation.NavRoutes
import com.chalupin.weather.presentation.home.ui.HomeScreen


@Composable
fun AppNavigationHost(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME_SCREEN,
    ) {
        composable(
            NavRoutes.HOME_SCREEN,
        ) {
            HomeScreen(
                viewModel = hiltViewModel(),
                snackBarHostState,
            )
        }
        composable(
            NavRoutes.SEARCH_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(durationMillis = 400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(600)
                )
            },
        ) {
//            SearchScreen(
//                viewModel = hiltViewModel(),
//            )
        }
    }
}


