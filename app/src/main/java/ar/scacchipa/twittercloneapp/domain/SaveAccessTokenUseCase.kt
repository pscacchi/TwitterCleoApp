package ar.scacchipa.twittercloneapp.domain

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain
import ar.scacchipa.twittercloneapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveAccessTokenUseCase(
    private val sharedPreferences: SharedPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
): ISavedAccessTokenUseCase {
    override suspend operator fun invoke(token: UserAccessTokenDomain): Boolean {
        return withContext(dispatcher) {
            sharedPreferences.edit().apply {
                putString(Constants.TOKEN_TYPE, token.tokenType)
                putInt(Constants.EXPIRES_IN, token.expiresIn)
                putString(Constants.ACCESS_TOKEN, token.accessToken)
                putString(Constants.SCOPE, token.scope)
                putString(Constants.REFRESH_TOKEN, token.refreshToken)
            }.commit()
        }
    }
}

