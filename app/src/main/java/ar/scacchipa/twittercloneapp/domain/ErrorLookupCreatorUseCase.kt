package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.datasource.provideAuthSourceDateApi
import ar.scacchipa.twittercloneapp.datasource.provideRetrofit
import ar.scacchipa.twittercloneapp.repository.AuthorizationRepository
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository

class ErrorLookupCreatorUseCase(
    private val repository: IAuthorizationRepository =
        AuthorizationRepository(provideAuthSourceDateApi(provideRetrofit()))
) {
    operator fun invoke():UserAccessToken {
        return repository.getErrorLookupToken()
    }
}