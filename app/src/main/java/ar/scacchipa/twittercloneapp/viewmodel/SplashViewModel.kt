package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.ViewModel
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import kotlinx.coroutines.CoroutineScope

class SplashViewModel(
    var composeScope: CoroutineScope,
    var onFinishTime: () -> Unit = {},
    var timer: SplashTimerUseCase = SplashTimerUseCase(
        onFinishTime,
        composeScope
    )
): ViewModel() {
    fun spendSplash() {
        timer.startToSpend()
    }
}