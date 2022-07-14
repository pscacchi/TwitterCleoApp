package ar.scacchipa.twittercloneapp.viewmodel

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
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
import java.net.URI

class AuthWebDialogViewModel (
    private val authorizationUseCase: AuthorizationUseCase =
        AuthorizationUseCase(AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit()))),
    private val consumableAuthUseCase: ConsumableAuthUseCase = ConsumableAuthUseCase(),
): ViewModel() {

    private val _userAccessToken = MutableLiveData<UserAccessToken>()
    val userAccessToken: LiveData<UserAccessToken>
        get() = _userAccessToken

    fun onReceiveUrl(uri: URI) {
        if (uri.host == URI(Constants.REDIRECT_URI).host) {
            val queryParameters = getQueryParameters(uri.query)
            queryParameters["code"]?.let { code ->
                this.generateUserAccessToken(code)
            }
            queryParameters["error"]?.let { error ->
                if (error == "access_denied") {
                    _userAccessToken.value = UserAccessToken(error = Constants.ERROR_CANCELLED_AUTH)                }
            }
        }
    }
    fun onErrorAuthorization(request: WebResourceRequest?,
                             errorResponse: WebResourceResponse?) {
        if (request?.url.toString() == "https://mobile.twitter.com/i/api/1.1/onboarding/task.json"
            && (errorResponse?.statusCode == 400 || errorResponse?.statusCode == 401)) {
            println("${request?.url.toString()} -> ${errorResponse.statusCode}")
            _userAccessToken.value = UserAccessToken(error = Constants.ERROR_NO_AUTHORIZATION)
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
    private fun getQueryParameters(query: String): HashMap<String, String> {
        val params = query.split("&")

        val map = hashMapOf<String, String>()

        params.forEach { param ->
            val term = param.split("=")
            val name = term[0]
            val value = term[1]
            map[name] = value
        }
        return map
    }
    fun createTemporaryCodeUrl(): String {
        return consumableAuthUseCase()
    }
}
