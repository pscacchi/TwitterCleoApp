package ar.scacchipa.twittercloneapp.domain;

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashTimer(
    val callback: () -> Unit,
    val scope: CoroutineScope?) {
    init {
        scope?.launch {
            delay(5000)
            callback.invoke()
        }
    }
}
