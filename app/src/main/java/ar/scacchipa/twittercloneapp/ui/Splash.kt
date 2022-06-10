package ar.scacchipa.twittercloneapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TwitterLogo()
        LaunchedEffect(key1 = true) {
                delay(3000)
                navController.navigate(route = Route.Login.route) {
                    this.popUpTo(route = Route.Splash.route) {
                        inclusive = true
                    }
                }
        }
    }
}
