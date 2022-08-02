package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.ResponseDomain

interface IAuthorizationRepository {
    suspend fun requestAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): ResponseDomain
}