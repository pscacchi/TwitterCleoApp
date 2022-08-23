package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockSharedPreferences
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import org.junit.Assert
import org.junit.Test

class CheckCredentialsCaseTest {

    private val mockSharedPrefs = MockSharedPreferences()
    private val subject = CheckCredentialsUseCase(
        sharedPrefs = mockSharedPrefs
    )

    @Test
    fun subjectFindCredentials() {

        val token = MockTokenProvider.userAccessTokenDomain1()

        mockSharedPrefs.edit().apply {
            putString(Constants.TOKEN_TYPE, token.tokenType)
            putInt(Constants.EXPIRES_IN, token.expiresIn)
            putString(Constants.ACCESS_TOKEN, token.accessToken)
            putString(Constants.SCOPE, token.scope)
            putString(Constants.REFRESH_TOKEN, token.refreshToken)
        }.commit()

        Assert.assertTrue(
            subject()
        )

        mockSharedPrefs
            .edit()
            .clear()
            .commit()
    }

    @Test
    fun subjectNotFindCredentials() {
        Assert.assertFalse(
            subject()
        )
    }
}

