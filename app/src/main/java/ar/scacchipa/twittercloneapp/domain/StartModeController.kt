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
    init {
        splashCallback()
        scope.launch {
            delay(5000)
            loginCallback()
        }
    }
    companion object {
        fun getInitialScreen() = Route.SplashScreen.route
    }
}
