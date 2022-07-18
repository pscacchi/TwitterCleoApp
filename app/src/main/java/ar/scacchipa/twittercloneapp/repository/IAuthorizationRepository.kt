package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken

interface IAuthorizationRepository {
    suspend fun requestAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): UserAccessToken
    fun getCancelledAuthToken() : UserAccessToken
    fun getErrorLookupToken() : UserAccessToken
}