package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain

interface ICredentialRepository {
    suspend fun storeLocalCredential(credential: Credential): Boolean
    fun recoverLocalCredential(): Credential?
    suspend fun getExternalCredential(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): ResponseDomain
}