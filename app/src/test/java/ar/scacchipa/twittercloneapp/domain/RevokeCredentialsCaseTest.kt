package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.data.RevokeDomain
import ar.scacchipa.twittercloneapp.repository.IRevokeTokenRepository
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockSharedPreferences
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Test

@ExperimentalCoroutinesApi
class RevokeCredentialsCaseTest {

    private val mockSharedPrefs = MockSharedPreferences()

    private val subject = RevokeCredentialsUseCase(
        repository = MockRevokeTokenRepository(),
        sharedPreferences = mockSharedPrefs,
        dispatcher = Dispatchers.Default
    )

    @Test
    fun invokeDeleteFromSharedPreference() = runTest {
        val token = MockTokenProvider.userAccessTokenDomain1()
        mockSharedPrefs.edit().apply {
            putString(Constants.TOKEN_TYPE, token.tokenType)
            putInt(Constants.EXPIRES_IN, token.expiresIn)
            putString(Constants.ACCESS_TOKEN, token.accessToken)
            putString(Constants.SCOPE, token.scope)
            putString(Constants.REFRESH_TOKEN, token.refreshToken)
        }
        subject(
            MockTokenProvider.userAccessTokenDomain1().accessToken,
            Constants.CLIENT_ID
        )

        assertFalse(mockSharedPrefs.contains(Constants.TOKEN_TYPE))
        assertFalse(mockSharedPrefs.contains(Constants.EXPIRES_IN))
        assertFalse(mockSharedPrefs.contains(Constants.ACCESS_TOKEN))
        assertFalse(mockSharedPrefs.contains(Constants.SCOPE))
        assertFalse(mockSharedPrefs.contains(Constants.REFRESH_TOKEN))

        mockSharedPrefs
            .edit()
            .clear()
            .commit()
    }

    class MockRevokeTokenRepository: IRevokeTokenRepository {
        override suspend fun revokeToken(token: String, clientId: String): ResponseDomain {
            return if (
                token == MockTokenProvider.userAccessTokenDomain1().accessToken
                && clientId == Constants.CLIENT_ID
            ) {
                ResponseDomain.Success( RevokeDomain( true ) )
            } else {
                ResponseDomain.Error(
                    error = Constants.ERROR_NO_REVOKE_ACCESS_TOKEN,
                    errorDescription = "Error"
                )
            }
        }
    }
}