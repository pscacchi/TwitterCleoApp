package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit

class AuthorizationRepository(
    val genAccessTokenSource: IAuthDataSource = provideAuthSourceDateApi(provideRetrofit())
): IAuthorizationRepository {
    override suspend fun genAccessToken(
        transitoryToken: String,
        grant_type: String,
        clientId: String,
        redirect_uri: String,
        codeVerifier: String,
        state: String
    ): UserAccessToken {
        val response = genAccessTokenSource.genAccessTokenSourceCode(
            transitoryToken = transitoryToken,
            grant_type = grant_type,
            clientId = clientId,
            redirect_uri = redirect_uri,
            codeVerifier = codeVerifier,
            state = state)
        return if (response.isSuccessful) {
            response.body()?:UserAccessToken()
        } else {
            UserAccessToken()
        }
    }
}