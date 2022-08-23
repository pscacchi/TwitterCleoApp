package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain

interface ISavedCredentialsUseCase {
    suspend operator fun invoke(token: UserAccessTokenDomain): Boolean
}