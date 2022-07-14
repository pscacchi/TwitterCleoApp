package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _mustShowErrorMsg = MutableLiveData(false)
    private val _navToFragAuthWeb = MutableLiveData(false)

    fun showErrorMsg() {
        _mustShowErrorMsg.value = true
    }

    fun onClickMsgButton() {
        _mustShowErrorMsg.value = false
    }

    fun addMsgChangeObserver( owner: LifecycleOwner, observer: (Boolean) -> Unit) {
        _mustShowErrorMsg.observe(owner, observer)
    }

    fun onNavToAuthWeb() {
        _navToFragAuthWeb.value = false
    }

    fun onClickLoginButton() {
        _navToFragAuthWeb.value = true
    }

    fun addNavigatorObserver(owner: LifecycleOwner, observer: (Boolean) -> Unit) {
        _navToFragAuthWeb.observe(owner, observer)
    }
}