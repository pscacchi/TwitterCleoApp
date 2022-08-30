package ar.scacchipa.twittercloneapp.data.repository

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