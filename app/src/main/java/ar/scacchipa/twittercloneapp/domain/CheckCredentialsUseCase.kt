package ar.scacchipa.twittercloneapp.domain

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.utils.Constants

class CheckCredentialsUseCase(
    private val sharedPrefs: SharedPreferences
): ICheckCredentialsUseCase {
    override operator fun invoke(): Boolean {
        return (sharedPrefs.getString(Constants.TOKEN_TYPE, "") ?: "").isNotEmpty()
    }
}