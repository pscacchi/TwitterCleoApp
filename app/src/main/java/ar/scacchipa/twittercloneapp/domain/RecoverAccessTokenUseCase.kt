package ar.scacchipa.twittercloneapp.domain

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain
import ar.scacchipa.twittercloneapp.utils.Constants

class RecoverAccessTokenUseCase(
    private val sharedPrefs: SharedPreferences
): IRecoveredAccessTokenUseCase {
    override operator fun invoke(): UserAccessTokenDomain {
        sharedPrefs.apply {
            return UserAccessTokenDomain(
                tokenType = getString(Constants.TOKEN_TYPE, "") ?: "",
                expiresIn = getInt(Constants.EXPIRES_IN, 0),
                accessToken = getString(Constants.ACCESS_TOKEN, "") ?: "",
                scope = getString(Constants.SCOPE, "") ?: "",
                refreshToken = getString(Constants.REFRESH_TOKEN, "") ?: ""
            )
        }
    }
}