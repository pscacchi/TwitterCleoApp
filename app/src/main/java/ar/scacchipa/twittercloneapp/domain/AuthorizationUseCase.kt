package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.TokenResource
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository

open class AuthorizationUseCase(
    private val repository: IAuthorizationRepository =
        AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit()))
) {
    open suspend operator fun invoke(
        transitoryToken: String
    ): TokenResource {
        return repository.requestAccessToken(
            transitoryToken = transitoryToken,
            grantType = Constants.GRANT_TYPE_AUTHORIZATION_CODE,
            clientId = Constants.CLIENT_ID,
            redirectUri = Constants.REDIRECT_URI,
            codeVerifier = Constants.CODE_VERIFIER_CHALLENGE,
            state = Constants.STATE_STATE)
    }
}