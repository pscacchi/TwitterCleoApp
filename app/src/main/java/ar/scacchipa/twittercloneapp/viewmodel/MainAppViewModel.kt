package ar.scacchipa.twittercloneapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.StartModeController
import ar.scacchipa.twittercloneapp.ui.Route
import kotlinx.coroutines.flow.MutableStateFlow

class MainAppViewModel(app: Application): AndroidViewModel( app ) {
    private val startModeController = StartModeController(
        scope = viewModelScope,
        splashCallback = { currentPage.value = Route.SplashScreen.route },
        loginCallback = { currentPage.value = Route.LoginScreen.route }
    )
    var currentPage: MutableStateFlow<String> = MutableStateFlow(
        startModeController.getInitialScreen()
    )
}