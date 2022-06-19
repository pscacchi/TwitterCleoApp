package ar.scacchipa.twittercloneapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.StartModeController
import ar.scacchipa.twittercloneapp.ui.Route
import kotlinx.coroutines.flow.MutableStateFlow

class MainAppViewModel: ViewModel( ) {
    var currentPage: MutableStateFlow<String> = MutableStateFlow(
        StartModeController.getInitialScreen()
    )
    init {
        Log.i("ViewModel", "Reinitial Viewmodel")
        StartModeController(
            scope = viewModelScope,
            splashCallback = { navigateTo(Route.SplashScreen.route) },
            loginCallback = { navigateTo(Route.LoginScreen.route) }
        )
    }
    fun navigateTo(route: String) {
        currentPage.value = route
    }
}