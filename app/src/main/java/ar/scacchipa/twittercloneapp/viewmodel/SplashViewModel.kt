package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.ICheckCredentialUseCase
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashTimerUseCase: SplashTimerUseCase,
    private val checkCredentialsUseCase: ICheckCredentialUseCase
): ViewModel() {

    private val _splashWasSpent = MutableLiveData(false)
    val splashWasSpent = _splashWasSpent as LiveData<Boolean>

    fun spendSplash() {
        viewModelScope.launch {
            _splashWasSpent.value = splashTimerUseCase.spendSplash()
        }
    }

    fun onCheckCredentials(): Boolean {
        return checkCredentialsUseCase()
    }
}