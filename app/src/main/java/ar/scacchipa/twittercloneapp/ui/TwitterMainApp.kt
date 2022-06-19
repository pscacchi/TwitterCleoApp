package ar.scacchipa.twittercloneapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme
import ar.scacchipa.twittercloneapp.viewmodel.MainAppViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TwitterMainApp(
    viewModel: MainAppViewModel = viewModel()
) {
    val navController = rememberNavController()
    TwitterCloneAppTheme {
        NavHost(
            navController = navController,
            startDestination = viewModel.currentPage.collectAsState().value
        ) {
            composable(Route.SplashScreen.route) {
                SplashScreen(callback = {

                })
            }
            composable(Route.LoginScreen.route) {
                LoginScreen(callback = {
                    navController.navigate(Route.MainScreen.route)
                })
            }
            composable(Route.MainScreen.route) {
                MainScreen(callback = {

                })
            }
        }
    }
    LocalLifecycleOwner.current.lifecycleScope.launchWhenStarted {
        viewModel.currentPage.collectLatest {
            navController.navigate(it) {
                this.popUpTo(route = Route.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}


