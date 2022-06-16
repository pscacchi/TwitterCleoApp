package ar.scacchipa.twittercloneapp.domain;

import ar.scacchipa.twittercloneapp.ui.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartModeController(
    val scope: CoroutineScope,
    val splashCallback: () -> Unit,
    val loginCallback: () -> Unit
    ) {

    private var splashWasShowed = false

    init {
        if (splashWasShowed) {
            loginCallback()
        } else {
            splashCallback
            scope.launch {
                delay(5000)
                loginCallback()
            }
        }
    }

    fun getInitialScreen() =
        if (splashWasShowed)
            Route.LoginScreen.route
        else
            Route.SplashScreen.route
}
