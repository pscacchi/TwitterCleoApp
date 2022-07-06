package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.DbContants
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository

open class AuthorizationUseCase(
    private val repository: IAuthorizationRepository =
        AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit()))
) {
    open suspend operator fun invoke(
        transitoryToken: String,
    ): UserAccessToken {
        return repository.genAccessToken(
            transitoryToken = transitoryToken,
            grantType = DbContants.GRANT_TYPE_AUTHORIZATION_CODE,
            clientId = DbContants.CLIENT_ID,
            redirectUri = DbContants.REDIRECT_URI,
            codeVerifier = DbContants.CODE_VERIFIER_CHALLENGE,
            state = DbContants.STATE_STATE)
    }
}