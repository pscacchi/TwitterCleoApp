package ar.scacchipa.twittercloneapp.domain;

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashTimerUseCase(
    val callback: () -> Unit,
    val scope: CoroutineScope) {
    fun startToSpend() {
        scope.launch {
            delay(5000)
            callback.invoke()
        }
    }
}
