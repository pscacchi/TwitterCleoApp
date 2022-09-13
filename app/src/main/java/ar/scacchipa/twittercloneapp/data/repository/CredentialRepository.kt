package ar.scacchipa.twittercloneapp.data.repository

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class CredentialRepository(
    private val credentialLocalSource: SharedPreferences,
    private val accessTokenExternalSource: IAuthDataSource,
    private val mapper: IMapper<UserAccessToken, Credential>,
    private val dispatcherDefault: CoroutineDispatcher = Dispatchers.Default,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
): ICredentialRepository {

    override suspend fun recoverLocalCredential(): Credential? {
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

    override suspend fun storeLocalCredential(credential: Credential): Boolean {
        return withContext(dispatcherDefault) {
            credentialLocalSource.edit().apply {
                putString(Constants.ACCESS_TOKEN, credential.accessToken)
                putString(Constants.REFRESH_TOKEN, credential.refreshToken)
            }.commit()
        }
    }
    override suspend fun getExternalCredential(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): ResponseDomain {
        return withContext(dispatcherIO) {
            try {
                val token = accessTokenExternalSource.genAccessTokenSourceCode(
                    transitoryToken = transitoryToken,
                    grantType = grantType,
                    clientId = clientId,
                    redirectUri = redirectUri,
                    codeVerifier = codeVerifier,
                    state = state
                )
                if (token.isSuccessful) {
                    token.body()?.let { userAccessToken ->
                        ResponseDomain.Success(
                            mapper.toDomain(userAccessToken)
                        )
                    } ?: ResponseDomain.Error()
                } else {
                    ResponseDomain.Error(
                        error = Constants.ERROR_HOST_LOOKUP_TOKEN,
                        errorDescription = token.body()?.errorDescription ?: ""
                    )
                }
            } catch (e: Exception) {
                ResponseDomain.Exception(message = e.message ?: "")
            }
        }
    }
}


