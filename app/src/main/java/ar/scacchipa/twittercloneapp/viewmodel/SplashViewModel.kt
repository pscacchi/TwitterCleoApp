package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashTimer: SplashTimerUseCase = SplashTimerUseCase()
): ViewModel() {
    val splashWasSpent = MutableLiveData(false)

    fun spendSplash() {
        viewModelScope.launch {
            splashWasSpent.value = splashTimer.spendSplash()
        }
    }
}