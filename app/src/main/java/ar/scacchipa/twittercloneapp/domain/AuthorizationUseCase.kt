package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository

class AuthorizationUseCase(
    private val repository: IAuthorizationRepository =
        AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit()))
) {
    suspend operator fun invoke(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state:String
    ): UserAccessToken {
        return repository.genAccessToken(
            transitoryToken = transitoryToken,
            grantType = grantType,
            clientId = clientId,
            redirectUri = redirectUri,
            codeVerifier = codeVerifier,
            state = state)
    }
}