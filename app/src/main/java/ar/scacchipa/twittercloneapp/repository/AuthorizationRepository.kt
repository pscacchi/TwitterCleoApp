package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthorizationRepository(
    private val genAccessTokenSource: IAuthDataSource = provideAuthSourceDateApi(provideRetrofit()),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): IAuthorizationRepository {
    override suspend fun requestAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): UserAccessToken = withContext(dispatcher){
        val response = genAccessTokenSource.genAccessTokenSourceCode(
            transitoryToken = transitoryToken,
            grantType = grantType,
            clientId = clientId,
            redirectUri = redirectUri,
            codeVerifier = codeVerifier,
            state = state)
        if (response.isSuccessful) {
            response.body()?:UserAccessToken()
        } else {
            UserAccessToken(error = Constants.ERROR_HOST_LOOKUP_TOKEN)
        }
    }
}