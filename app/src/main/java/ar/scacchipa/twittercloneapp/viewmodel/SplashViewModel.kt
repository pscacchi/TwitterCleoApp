package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashTimer: SplashTimerUseCase = SplashTimerUseCase()
): ViewModel() {
    private val _splashWasSpent = MutableLiveData(false)

    fun spendSplash() {
        viewModelScope.launch {
            _splashWasSpent.value = splashTimer.spendSplash()
        }
    }
    fun getSplashWasSpent(): Boolean {
        return _splashWasSpent.value?:false
    }
    fun addObserver(owner: LifecycleOwner, observer: (Boolean) -> Unit) {
        _splashWasSpent.observe(owner, observer)
    }
}