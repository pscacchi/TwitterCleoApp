package ar.scacchipa.twittercloneapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit
import ar.scacchipa.twittercloneapp.domain.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.ConsumableAuthUseCase
import ar.scacchipa.twittercloneapp.domain.ErrorTokenCreatorUseCase
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.Constants
import kotlinx.coroutines.launch
import java.net.URI

class AuthWebDialogViewModel (
    private val authorizationUseCase: AuthorizationUseCase =
        AuthorizationUseCase(AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit()))),
    private val consumableAuthUseCase: ConsumableAuthUseCase = ConsumableAuthUseCase(),
    private val errorUserCaseTokenCreator: ErrorTokenCreatorUseCase =
        ErrorTokenCreatorUseCase(AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit())))

): ViewModel() {

    private val _userAccessToken = MutableLiveData<UserAccessToken>()
    val userAccessToken: LiveData<UserAccessToken>
        get() = _userAccessToken

    fun controlRequest(uri: URI) {
        if (uri.host == URI(Constants.REDIRECT_URI).host) {
            val queryParameters = getQueryParameters(uri.query)
            queryParameters["code"]?.let { code ->
                this.generateUserAccessToken(code)
            }
            queryParameters["error"]?.let { error ->
                if (error == "access_denied") {
                    _userAccessToken.value = errorUserCaseTokenCreator()
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
