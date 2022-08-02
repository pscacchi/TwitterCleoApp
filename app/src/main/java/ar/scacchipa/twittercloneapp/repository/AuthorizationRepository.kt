package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthorizationRepository(
    private val genAccessTokenSource: IAuthDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): IAuthorizationRepository {
    override suspend fun requestAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): TokenResource = withContext(dispatcher) {
        try {
            val response = genAccessTokenSource.genAccessTokenSourceCode(
                transitoryToken = transitoryToken,
                grantType = grantType,
                clientId = clientId,
                redirectUri = redirectUri,
                codeVerifier = codeVerifier,
                state = state
            )
            if (response.isSuccessful) {
                response.body()?.let { userAccessToken ->
                    TokenResource.Success(
                        tokenType = userAccessToken.tokenType,
                        expiresIn = userAccessToken.expiresIn,
                        accessToken = userAccessToken.accessToken,
                        scope = userAccessToken.scope,
                        refreshToken = userAccessToken.refreshToken
                    )
                } ?: TokenResource.Error()
            } else {
                TokenResource.Error(
                    error = Constants.ERROR_HOST_LOOKUP_TOKEN,
                    errorDescription = response.body()?.errorDescription?:""
                )
            }
        } catch (e: Exception) {
            TokenResource.Exception(message = e.message?:"")
        }
    }
}
