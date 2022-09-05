package ar.scacchipa.twittercloneapp.data.repository

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class CredentialRepository(
    private val credentialLocalSource: SharedPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
): ICredentialRepository {

    override fun recoverCredential(): Credential? {
        credentialLocalSource.apply {
            if ( !contains(Constants.ACCESS_TOKEN)
                || !contains(Constants.REFRESH_TOKEN) ) {
                return null
            }
            return Credential(
                getString(Constants.ACCESS_TOKEN, "") ?: "",
                getString(Constants.REFRESH_TOKEN, "") ?: ""
            )
        }
    }

    override suspend fun storeCredential(credential: Credential): Boolean {
        return withContext(dispatcher) {
            credentialLocalSource.edit().apply {
                putString(Constants.ACCESS_TOKEN, credential.accessToken)
                putString(Constants.REFRESH_TOKEN, credential.refreshToken)
            }.commit()
        }
    }
}


