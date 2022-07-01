package ar.scacchipa.twittercloneapp.viewmodel

import android.net.Uri
import androidx.core.net.toUri
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
    var userAccessToken = MutableLiveData<UserAccessToken>()
        private set

    fun controlRequest(uri: Uri, clientId: String, redirectUri: String) {
        if (uri.host == redirectUri.toUri().host) {
            uri.getQueryParameter("code")?.let { code ->
                this.generateUserAccessToken(code, clientId, redirectUri)
            }
        }
    }

    private fun generateUserAccessToken(transitoryCode: String, clientId: String, redirectUri: String) {
        viewModelScope.launch {
            userAccessToken.value = withContext(Dispatchers.IO) {
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
