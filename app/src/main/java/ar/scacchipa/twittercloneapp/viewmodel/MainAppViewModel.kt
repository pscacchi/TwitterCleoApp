package ar.scacchipa.twittercloneapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import ar.scacchipa.twittercloneapp.ui.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainAppViewModel(app: Application): AndroidViewModel( app ) {

    val sharedPreference = PreferenceManager.getDefaultSharedPreferences( app )
    var splashWasShowed = sharedPreference.getBoolean("splashWasShowed", false)

    var currentPage: MutableStateFlow<String> = MutableStateFlow( getInitialScreen() )

    init {
        if (!splashWasShowed) {
            viewModelScope.launch {
                delay(5000)
                currentPage.value = Route.LoginScreen.route

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