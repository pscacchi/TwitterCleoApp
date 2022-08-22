package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain

interface ISavedAccessTokenUseCase {
    suspend operator fun invoke(token: UserAccessTokenDomain): Boolean
}