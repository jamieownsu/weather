import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chalupin.practice.core.navigation.NavRoutes
import com.chalupin.practice.presentation.home.ui.HomeScreen
import com.chalupin.practice.presentation.search.ui.SearchScreen


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
                navController,
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
            SearchScreen(
//                viewModel = hiltViewModel(),
            )
        }
    }
}


