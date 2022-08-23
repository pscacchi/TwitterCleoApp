package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.utils.Constants.Companion.ACCESS_TOKEN
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.EXPIRES_IN
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.REFRESH_TOKEN
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.SCOPE
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.TOKEN_TYPE
import ar.scacchipa.twittercloneapp.utils.MockSharedPreferences
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveAccessTokenUseCaseTest {
    private val mockSharedPrefs = MockSharedPreferences()

    private val subject = SaveCredentialsUseCase(
        sharedPreferences = mockSharedPrefs,
        dispatcher = Dispatchers.Default
    )

    @Test
    fun saveTokenOnSharedPrefs() = runTest {
        val token = MockTokenProvider.userAccessTokenDomain1()

        subject.invoke(token)

        assertEquals(
            token.tokenType,
            mockSharedPrefs.getString(TOKEN_TYPE, null)
        )
        assertEquals(
            token.expiresIn,
            mockSharedPrefs.getInt(EXPIRES_IN, 0)
        )
        assertEquals(
            token.accessToken,
            mockSharedPrefs.getString(ACCESS_TOKEN, null)
        )
        assertEquals(
            token.scope,
            mockSharedPrefs.getString(SCOPE, null)
        )
        assertEquals(
            token.refreshToken,
            mockSharedPrefs.getString(REFRESH_TOKEN, null)
        )

        mockSharedPrefs
            .edit()
            .clear()
            .commit()
    }
}