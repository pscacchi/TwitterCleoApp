package ar.scacchipa.twittercloneapp.domain

interface ICheckCredentialsUseCase {
    operator fun invoke(): Boolean
}