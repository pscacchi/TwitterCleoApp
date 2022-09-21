package ar.scacchipa.twittercloneapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.usecase.RevokeCredentialUseCase
import kotlinx.coroutines.launch

class MainActivityViewModel (
    val revokeCredentialUseCase: RevokeCredentialUseCase
): ViewModel() {

    private val _credentialStatus = MutableLiveData(true)
    val credentialStatus: LiveData<Boolean> = _credentialStatus

    fun onLogOut() {
        viewModelScope.launch {
            _credentialStatus.value = revokeCredentialUseCase().not()
        }
    }
}