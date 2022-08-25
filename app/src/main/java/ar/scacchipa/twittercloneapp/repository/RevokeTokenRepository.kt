package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.IRevokeMapper
import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.datasource.IRevokeTokenDataSource
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RevokeTokenRepository(
    private val revokeTokenDataSource: IRevokeTokenDataSource,
    private val mapper: IRevokeMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): IRevokeTokenRepository {
    override suspend fun revokeToken(
        token: String,
        clientId: String
    ): ResponseDomain {
        return withContext(dispatcher) {
            revokeTokenDataSource.revokeToken(
                token,
                clientId,
                Constants.TOKEN_TYPE_HINT
            ).let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { revokeData ->
                        ResponseDomain.Success(
                            mapper.toDomain(revokeData)
                        )
                    } ?: ResponseDomain.Error()
                } else {
                    ResponseDomain.Error(
                        error = Constants.ERROR_NO_REVOKE_ACCESS_TOKEN,
                        errorDescription = response.errorBody()?.charStream().toString()
                    )
                }
            }
        }
    }
}