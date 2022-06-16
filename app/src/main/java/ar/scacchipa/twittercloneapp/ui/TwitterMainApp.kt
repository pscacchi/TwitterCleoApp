package ar.scacchipa.twittercloneapp.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.scacchipa.twittercloneapp.ui.theme.TwitterCloneAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainAppViewModel: ViewModel() {
    var currentPage: MutableStateFlow<String> = MutableStateFlow(Route.SplashScreen.route)

    init {
        Log.i("ViewModel", "init")
        viewModelScope.launch {
            while(true) {
                delay(1000)
                toggle()
            }
        }
    }
    fun toggle() {
        currentPage.value = if (currentPage.value == Route.SplashScreen.route) {
            Route.LoginScreen.route
        } else {
            Route.SplashScreen.route
        }
        Log.i("ViewModel", currentPage.value)
    }
}

@Composable
fun TwitterMainApp(
    viewModel: MainAppViewModel = viewModel()
) {
    Log.i("TwitterMainApp", "Recomposing")
    Log.i("TwitterMainApp", viewModel.toString())

    TwitterCloneAppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = viewModel.currentPage.value ?: Route.SplashScreen.route
        ) {
            composable(Route.SplashScreen.route) {
                SplashScreen(callback = {
                    viewModel.toggle()
                })
            }
            composable(Route.LoginScreen.route) {
                LoginScreen(callback = {
                    viewModel.toggle()
                })
            }
        }

        LocalLifecycleOwner.current.lifecycleScope.launchWhenStarted {
            viewModel.currentPage.collectLatest {
                navController.navigate(it)
            }
        }
    }
}


