package ar.scacchipa.twittercloneapp.domain

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.data.RevokeDomain
import ar.scacchipa.twittercloneapp.repository.IRevokeTokenRepository
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RevokeTokenUseCase(
    private val repository: IRevokeTokenRepository,
    private val sharedPreferences: SharedPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    open suspend operator fun invoke(
        token: String,
        clientId: String
    ) {
        val response = repository.revokeToken(
            token = token,
            clientId = clientId
        )
        when (response) {
            is ResponseDomain.Success<*> -> {
                val revokeDomain = response.data as RevokeDomain

                if (revokeDomain.revoked) {
                    withContext(dispatcher) {
                        sharedPreferences.edit().apply {
                            remove(Constants.TOKEN_TYPE)
                            remove(Constants.EXPIRES_IN)
                            remove(Constants.ACCESS_TOKEN)
                            remove(Constants.SCOPE)
                            remove(Constants.REFRESH_TOKEN)
                        }.commit()
                    }
                }
            }
            else ->
                return
        }
    }
}