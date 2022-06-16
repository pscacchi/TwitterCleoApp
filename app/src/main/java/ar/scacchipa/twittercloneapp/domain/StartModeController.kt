package ar.scacchipa.twittercloneapp.domain;

import android.app.Application
import androidx.preference.PreferenceManager
import ar.scacchipa.twittercloneapp.ui.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartModeController(
    val app: Application,
    val scope: CoroutineScope,
    val splashCallback: () -> Unit,
    val loginCallback: () -> Unit
    ) {

    val sharedPreference = PreferenceManager.getDefaultSharedPreferences( app )
    var splashWasShowed = sharedPreference.getBoolean("splashWasShowed", false)

    init {
        if (splashWasShowed) {
            loginCallback()
        } else {
            splashCallback
            scope.launch {
                delay(5000)
                loginCallback()

                PreferenceManager
                    .getDefaultSharedPreferences(app)
                    .edit()
                    .putBoolean("splashWasShowed", true)
                    .apply()
            }
        }
    }

    fun getInitialScreen() =
        if (splashWasShowed)
            Route.LoginScreen.route
        else
            Route.SplashScreen.route
}
