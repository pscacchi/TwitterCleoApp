package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.domain.model.Credential

interface ICredentialRepository {
    suspend fun storeCredential(credential: Credential): Boolean
    fun recoverCredential(): Credential?
    suspend fun removeCredential()
}