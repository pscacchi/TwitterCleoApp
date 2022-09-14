package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.IMapper
import ar.scacchipa.twittercloneapp.data.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.data.datasource.ILocalSource
import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class CredentialRepository(
    private val credentialLocalSource: ILocalSource,
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
                get(Constants.ACCESS_TOKEN) ?: "",
                get(Constants.REFRESH_TOKEN) ?: ""
            )
        }
    }

    override suspend fun storeLocalCredential(credential: Credential): Boolean {
        return withContext(dispatcherDefault) {
            credentialLocalSource.apply {
                save(Constants.ACCESS_TOKEN, credential.accessToken)
                save(Constants.REFRESH_TOKEN, credential.refreshToken)
            }
            true
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


