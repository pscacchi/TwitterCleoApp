package ar.scacchipa.twittercloneapp.viewmodel

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit
import ar.scacchipa.twittercloneapp.domain.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.ConsumableAuthUseCase
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.Constants
import kotlinx.coroutines.launch

class AuthWebDialogViewModel (
    private val authorizationUseCase: AuthorizationUseCase =
        AuthorizationUseCase(AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit()))),
    private val consumableAuthUseCase: ConsumableAuthUseCase = ConsumableAuthUseCase()

): ViewModel() {

    private val _userAccessToken = MutableLiveData<UserAccessToken>()
    val userAccessToken: LiveData<UserAccessToken>
        get() = _userAccessToken

    fun controlRequest(uri: Uri) {
        if (uri.host == Constants.REDIRECT_URI.toUri().host) {
            uri.getQueryParameter("code")?.let { code ->
                this.generateUserAccessToken(code)
            }
            uri.getQueryParameter("error")?.let { error ->
                if (error == "access_denied") {
                    _userAccessToken.value = UserAccessToken()
                }
            }
        }
    }

    private fun generateUserAccessToken(
        transitoryCode: String
    ) {
        viewModelScope.launch {
            _userAccessToken.value = authorizationUseCase(
                    transitoryToken = transitoryCode,
            )
        }
    }

    fun createTemporaryCodeUrl(): String {
        return consumableAuthUseCase()
    }
}
