package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.ViewModel
import ar.scacchipa.twittercloneapp.domain.SplashTimer
import kotlinx.coroutines.CoroutineScope

class SplashViewModel: ViewModel() {
    var composeScope: CoroutineScope? = null
    var onFinishTime: () -> Unit = {}
    set(value) {
        field = value
        SplashTimer(
            callback = onFinishTime,
            scope = composeScope)
    }
}