package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken

interface IAuthorizationRepository {
    suspend fun genAccessToken(
        transitoryToken: String,
        grant_type: String = "authorization_code",
        clientId: String,
        redirect_uri: String,
        codeVerifier: String = "challenge",
        state: String = "state"
    ): UserAccessToken
}