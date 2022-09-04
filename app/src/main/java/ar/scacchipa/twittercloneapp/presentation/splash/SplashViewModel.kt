package ar.scacchipa.twittercloneapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.usecase.CheckInUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkInUseCase: CheckInUseCase
): ViewModel() {

    private val _mustLogin = MutableLiveData<Boolean?>()
    val mustLogin = _mustLogin as LiveData<Boolean?>

    fun spendSplash() {
        viewModelScope.launch {
            _mustLogin.value = checkInUseCase()
        }
    }
}