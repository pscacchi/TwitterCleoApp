package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val _mustShowErrorMsg = MutableLiveData(false)
    val mustShowErrorMsg = _mustShowErrorMsg as LiveData<Boolean>

    private val _navToFragAuthWeb = MutableLiveData(false)
    val navToFragAuthWeb = _navToFragAuthWeb as LiveData<Boolean>

    fun showErrorMsg() {
        _mustShowErrorMsg.value = true
    }

    fun onClickMsgButton() {
        _mustShowErrorMsg.value = false
    }

    fun onNavToAuthWeb() {
        _navToFragAuthWeb.value = false
    }

    fun onClickLoginButton() {
        _navToFragAuthWeb.value = true
    }
}