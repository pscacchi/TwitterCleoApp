package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.IRevokeCredentialUseCase
import kotlinx.coroutines.launch

class MainActivityViewModel(
    val revokeCredentialUseCase: IRevokeCredentialUseCase
) : ViewModel() {

    private val _credentialStatus = MutableLiveData(true)
    val credentialStatus: LiveData<Boolean> = _credentialStatus

    fun onLogOut() {
        viewModelScope.launch {
            _credentialStatus.value = revokeCredentialUseCase().not()
        }
    }
}