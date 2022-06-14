package ar.scacchipa.twittercloneapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme

@Composable
fun TwitterMainApp() {
    TwitterCloneAppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.SplashScreen.route
        ) {
            composable(Route.SplashScreen.route) {
                SplashScreen( callback = {
                    navController.navigate(Route.LoginScreen.route)
                } )
            }
            composable(Route.LoginScreen.route) {
                LoginScreen( callback = {
                    navController.navigate(Route.SplashScreen.route)
                } )
            }
        }

    }
}