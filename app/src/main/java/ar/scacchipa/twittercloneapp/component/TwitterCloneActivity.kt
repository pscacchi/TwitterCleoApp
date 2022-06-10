package ar.scacchipa.twittercloneapp.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.scacchipa.twittercloneapp.ui.Login
import ar.scacchipa.twittercloneapp.ui.Route
import ar.scacchipa.twittercloneapp.ui.Splash
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme

class TwitterCloneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwitterCloneAppTheme {
                val navController= rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.Splash.route
                ) {
                    composable(Route.Splash.route) { Splash(navController) }
                    composable(Route.Login.route) { Login(navController) }
                }
            }
        }
    }
}
