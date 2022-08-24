package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential

interface IStoreCredentialUseCase {
    suspend operator fun invoke(credential: Credential): Boolean
}