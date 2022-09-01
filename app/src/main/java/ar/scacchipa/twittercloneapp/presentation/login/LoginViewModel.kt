package ar.scacchipa.twittercloneapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _mustShowErrorMsg = MutableLiveData(false)
    val mustShowErrorMsg = _mustShowErrorMsg as LiveData<Boolean>

    private val _navToFragLoginWebSection = MutableLiveData(false)
    val navToFragLoginWebSection = _navToFragLoginWebSection as LiveData<Boolean>

    fun showErrorMsg() {
        _mustShowErrorMsg.value = true
    }

    fun onClickMsgButton() {
        _mustShowErrorMsg.value = false
    }

    fun onNavToLoginWebSection() {
        _navToFragLoginWebSection.value = false
    }

    fun onClickLoginButton() {
        _navToFragLoginWebSection.value = true
    }
}