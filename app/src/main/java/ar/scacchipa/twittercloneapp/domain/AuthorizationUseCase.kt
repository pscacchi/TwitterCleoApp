package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthorizationUseCase(
    private val repository: IAuthorizationRepository =
        AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit()))
) {
    suspend fun generateAccessToken(
        transitoryToken: String,
        grant_type: String = "authorization_code",
        clientId: String,
        redirect_uri: String,
        codeVerifier: String = "challenge",
        state:String = "state"
    ): UserAccessToken = withContext(Dispatchers.IO) {
        return@withContext repository.genAccessToken(
            transitoryToken = transitoryToken,
            grant_type = grant_type,
            clientId = clientId,
            redirect_uri = redirect_uri,
            codeVerifier = codeVerifier,
            state = state
        )
    }

    fun getScope():String  {
        return listOf("users.read", "tweet.read", "offline.access", "list.read", "follows.read",
            "like.read", "space.read","tweet.write", "like.write").joinToString("%20")
    }

    fun createTemporaryCodeUrl(clientId: String, redirectUri: String): String {
        return "https://twitter.com/i/oauth2/authorize?" +
                "response_type=code&" +
                "client_id=$clientId&" +
                "redirect_uri=$redirectUri&" +
                "scope=${getScope()}&" +
                "state=state&" +
                "code_challenge=challenge&" +
                "code_challenge_method=plain"
    }
}