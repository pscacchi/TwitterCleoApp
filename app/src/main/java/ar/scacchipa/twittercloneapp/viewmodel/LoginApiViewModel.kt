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
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginApiViewModel (
    private val userCase: AuthorizationUseCase =
        AuthorizationUseCase(AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit())))
): ViewModel() {
    private val _userAccessToken = MutableLiveData<UserAccessToken>()
    val userAccessToken: LiveData<UserAccessToken>
        get() = _userAccessToken

    fun controlRequest(uri: Uri, clientId: String, redirectUri: String) {
        if (uri.host == redirectUri.toUri().host) {
            uri.getQueryParameter("code")?.let { code ->
                this.generateUserAccessToken(code, clientId, redirectUri)
            }
            uri.getQueryParameter("error")?.let { error ->
                if (error == "access_denied") {
                    _userAccessToken.value = UserAccessToken()
                }
            }
        }
    }

    private fun generateUserAccessToken(
        transitoryCode: String,
        clientId: String,
        redirectUri: String
    ) {
        viewModelScope.launch {
            _userAccessToken.value = withContext(Dispatchers.IO) {
                userCase.generateAccessToken(
                    transitoryToken = transitoryCode,
                    grant_type = "authorization_code",
                    clientId = clientId,
                    redirect_uri = redirectUri,
                    codeVerifier = "challenge",
                    state = "state"
                )
            }
        }
    }

    fun createTemporaryCodeUrl(clientId: String, redirectUri: String): String {
        return userCase.createTemporaryCodeUrl(clientId, redirectUri)
    }
}
