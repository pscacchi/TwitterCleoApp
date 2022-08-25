package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.Credential

interface ICredentialLocalRepository {
    suspend fun storeCredential(credential: Credential): Boolean
    fun recoverCredential(): Credential
    suspend fun removeCredential()
}