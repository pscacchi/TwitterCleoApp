package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _showErrorMsgMLD = MutableLiveData(false)

    fun mustShowErrorMsg(show: Boolean) {
        _showErrorMsgMLD.value = show
    }

    fun addMsgChangeObserver( owner: LifecycleOwner, observer: (Boolean) -> Unit) {
        _showErrorMsgMLD.observe(owner, observer)
    }
}