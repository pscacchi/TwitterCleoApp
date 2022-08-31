package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain

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