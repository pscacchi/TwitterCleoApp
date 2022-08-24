package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.Credential

interface ICredentialRepository {
    suspend fun storeCredential(credential: Credential): Boolean
    fun recoverCredential(): Credential
    suspend fun removeCredential()
}