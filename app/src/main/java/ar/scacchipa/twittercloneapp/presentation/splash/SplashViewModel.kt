package ar.scacchipa.twittercloneapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.usecase.StarterUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val starterUseCase: StarterUseCase
): ViewModel() {

    private val _mustLogin = MutableLiveData<Boolean?>(null)
    val mustLogin = _mustLogin as LiveData<Boolean?>

    fun spendSplash() {
        viewModelScope.launch {
            _mustLogin.value = starterUseCase( )
        }
    }

    fun skipSplash() {
        viewModelScope.launch {
            _mustLogin.value = true
        }
    }
}