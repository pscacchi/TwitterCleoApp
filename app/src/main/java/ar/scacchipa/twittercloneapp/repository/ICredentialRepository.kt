package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.Credential

interface ICredentialRepository {
    suspend fun storeCredentials(credential: Credential): Boolean
    fun recoverCredentials(): Credential
    suspend fun removeCredential()
}