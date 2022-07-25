package ar.scacchipa.twittercloneapp.repository

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