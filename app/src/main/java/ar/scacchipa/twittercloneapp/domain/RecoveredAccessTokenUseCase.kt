package ar.scacchipa.twittercloneapp.domain

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain
import ar.scacchipa.twittercloneapp.utils.Constants

class RecoveredAccessTokenUseCase(
    private val sharedPreferences: SharedPreferences
) {
    open operator fun invoke(): UserAccessTokenDomain {
        sharedPreferences.apply {
            return UserAccessTokenDomain(
                tokenType = getString(Constants.TOKEN_TYPE, "") ?: "",
                expiresIn = getInt(Constants.EXPIRES_IN, 0),
                accessToken = getString(Constants.ACCESS_TOKEN, "") ?: "",
                scope = getString(Constants.SCOPE, "") ?: "",
                refreshToken = getString(Constants.REFRESH_TOKEN, "") ?: ""            )
        }
    }
}