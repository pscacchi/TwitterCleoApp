package ar.scacchipa.twittercloneapp.domain

interface IRevokeCredentialUseCase {
    suspend operator fun invoke(): Boolean
}