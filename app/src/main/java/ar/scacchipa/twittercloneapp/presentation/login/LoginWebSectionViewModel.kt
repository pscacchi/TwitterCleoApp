package ar.scacchipa.twittercloneapp.presentation.login

import android.webkit.WebResourceError
import android.webkit.WebViewClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.usecase.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.launch
import java.net.URI
import kotlin.collections.set

class LoginWebSectionViewModel (
    private val authorizationUseCase: AuthorizationUseCase
): ViewModel() {

    private val _responseDomain = MutableLiveData<ResponseDomain>()
    val responseDomain: LiveData<ResponseDomain> = _responseDomain

    fun onReceiveUrl(uri: URI) {
        if (uri.host == URI(Constants.REDIRECT_URI).host) {
            val queryParameters = getQueryParameters(uri.query)
            queryParameters[Constants.SERVER_PARAMETER_CODE]?.let { code ->
                this.getCredential(code)
            }
            queryParameters[Constants.SERVER_PARAMETER_ERROR]?.let { error ->
                if (error == Constants.ERROR_ACCESS_DENIED) {
                    _responseDomain.value = ResponseDomain.Cancel
                }
            }
        }
    }

    fun onReceivedWebError(error: WebResourceError?) {
        if (error?.errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
            _responseDomain.value = ResponseDomain.Error(error = Constants.ERROR_HOST_LOOKUP_TOKEN)
        }
    }

    private fun getCredential(
        transitoryCode: String
    ) {
        viewModelScope.launch {
            _responseDomain.value = authorizationUseCase(
                transitoryToken = transitoryCode
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

    fun getConsumableAuthCode(): String {
        return "https://twitter.com/i/oauth2/authorize?" +
                "response_type=${Constants.RESPONSE_TYPE_CODE}&" +
                "client_id=${Constants.CLIENT_ID}&" +
                "redirect_uri=${Constants.REDIRECT_URI}&" +
                "scope=${getScope()}&" +
                "state=${Constants.STATE}&" +
                "code_challenge=${Constants.CODE_CHALLENGE_CHALLENGE}&" +
                "code_challenge_method=${Constants.CODE_CHALLENGE_METHOD_PLAIN}"
    }

    private fun getScope(): String {
        return listOf(
            Constants.SCOPE_USERS_READ,
            Constants.SCOPE_TWEET_READ,
            Constants.SCOPE_TWEET_WRITE,
            Constants.SCOPE_OFFLINE_ACCESS,
            Constants.SCOPE_LIST_READ,
            Constants.SCOPE_FOLLOWS_READ,
            Constants.SCOPE_LIKE_READ,
            Constants.SCOPE_LIKE_WRITE,
            Constants.SCOPE_SPACE_READ
        ).joinToString("%20")
    }
}
