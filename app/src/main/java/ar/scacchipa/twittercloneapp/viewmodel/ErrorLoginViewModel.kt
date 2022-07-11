package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.scacchipa.twittercloneapp.domain.IsReadyToGoToLoginFragUseCase

class ErrorLoginViewModel(
    private val isReadyToGoToLoginFragUseCase: IsReadyToGoToLoginFragUseCase = IsReadyToGoToLoginFragUseCase()
): ViewModel() {
    private val _isReadyToGoToLoginFragMLD = MutableLiveData(false)
    val isReadyToGoToLoginFragLD = _isReadyToGoToLoginFragMLD as LiveData<Boolean>

    fun checkIsReadyToGoToLoginFrag() {
        _isReadyToGoToLoginFragMLD.value = isReadyToGoToLoginFragUseCase()
    }
}