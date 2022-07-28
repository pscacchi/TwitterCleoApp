package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.TokenResource

interface IAuthorizationRepository {
    suspend fun requestAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): TokenResource
}