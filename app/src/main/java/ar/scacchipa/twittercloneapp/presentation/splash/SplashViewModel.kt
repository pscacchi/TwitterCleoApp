package ar.scacchipa.twittercloneapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.usecase.StarterUseCase
import ar.scacchipa.twittercloneapp.presentation.splash.SplashState.ShowLogo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val starterUseCase: StarterUseCase
): ViewModel() {

    private val _splashState = MutableStateFlow<SplashState>(ShowLogo)
    val splashState = _splashState as StateFlow<SplashState>

    fun spendSplash() {
        viewModelScope.launch {
            _splashState.value = starterUseCase()
        }
    }
}