package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken

interface IAuthorizationRepository {
    suspend fun genAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): UserAccessToken
}