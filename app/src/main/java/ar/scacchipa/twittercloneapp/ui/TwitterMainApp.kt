package ar.scacchipa.twittercloneapp.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainAppViewModel: ViewModel() {
    var currentPage by mutableStateOf(Route.SplashScreen.route)
    init {
        Log.i("ViewModel", "init")
        viewModelScope.launch {
            Log.i("ViewModel", "Launch toogle loop")
            while(true) {
                delay(1000)
                toggle()
            }
        }
    }
    fun toggle() {
        currentPage = if (currentPage == Route.SplashScreen.route) {
            Route.LoginScreen.route
        } else {
            Route.SplashScreen.route
        }
    }
}

@Composable
fun TwitterMainApp(
    viewModel: MainAppViewModel = viewModel()
) {
    //val currentPage = viewModel.currentPage
    Log.i("TwitterMainApp", "Recomposing")
    Log.i("TwitterMainApp", viewModel.toString())
    Log.i("TwitterMainApp", viewModel.currentPage)
    TwitterCloneAppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = viewModel.currentPage
        ) {
            composable(Route.SplashScreen.route) {
                SplashScreen( callback = {
//                    navController.navigate(Route.LoginScreen.route)
                } )
            }
            composable(Route.LoginScreen.route) {
                LoginScreen( callback = {
//                    navController.navigate(Route.SplashScreen.route)
                } )
            }
        }
    }
}