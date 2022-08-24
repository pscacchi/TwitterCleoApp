package ar.scacchipa.twittercloneapp.repository

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CredentialRepository(
    private val credentialLocalSource: SharedPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
): ICredentialRepository {

    override fun recoverCredentials(): Credential {
        credentialLocalSource.apply {
            return Credential(
                getString(Constants.ACCESS_TOKEN, "") ?: "",
                getString(Constants.REFRESH_TOKEN, "") ?: ""
            )
        }
    }

    override suspend fun storeCredentials(credential: Credential): Boolean {
        return withContext(dispatcher) {
            credentialLocalSource.edit().apply {
                putString(Constants.ACCESS_TOKEN, credential.accessToken)
                putString(Constants.REFRESH_TOKEN, credential.refreshToken)
            }.commit()
        }
    }

    override suspend fun removeCredential() {
        withContext(dispatcher) {
            credentialLocalSource.edit().apply {
                remove(Constants.ACCESS_TOKEN)
                remove(Constants.REFRESH_TOKEN)
            }.commit()
        }
    }
}


