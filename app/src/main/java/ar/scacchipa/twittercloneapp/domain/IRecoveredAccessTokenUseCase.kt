package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain

interface IRecoveredAccessTokenUseCase {
    operator fun invoke(): UserAccessTokenDomain
}