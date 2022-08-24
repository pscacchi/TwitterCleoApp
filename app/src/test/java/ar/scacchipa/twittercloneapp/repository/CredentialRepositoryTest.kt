package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockSharedPreferences
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFalse

@ExperimentalCoroutinesApi
class CredentialRepositoryTest {

    private val credentialLocalSource = MockSharedPreferences()

    val subject = CredentialRepository(
        credentialLocalSource = credentialLocalSource,
        dispatcher = Dispatchers.Default
    )

    @Test
    fun subjectRecoverStoredCredential() {
        val expectedCredential = MockTokenProvider.credential1()
        credentialLocalSource
            .edit()
            .apply {
                putString(Constants.ACCESS_TOKEN, expectedCredential.accessToken)
                putString(Constants.REFRESH_TOKEN, expectedCredential.refreshToken)
            }
            .commit()

        val actualCredential = subject.recoverCredential()

        assertEquals(
            expectedCredential,
            actualCredential
        )
        credentialLocalSource
            .edit()
            .clear()
            .commit()
    }

    @Test
    fun subjectLocalStoreCredential() = runTest {
        val expectedCredential = MockTokenProvider.credential1()

        subject.storeCredential(expectedCredential)

        val storedCredential = Credential(
            credentialLocalSource.getString(Constants.ACCESS_TOKEN, "") ?: "",
            credentialLocalSource.getString(Constants.REFRESH_TOKEN, "") ?: ""
        )

        assertEquals(
            expectedCredential,
            storedCredential
        )

        credentialLocalSource
            .edit()
            .clear()
            .commit()
    }

    @Test
    fun subjectDeleteCredentialFromLocalRepo() = runTest {
        val expectedCredential = MockTokenProvider.credential1()

        credentialLocalSource
            .edit()
            .apply {
                putString(Constants.ACCESS_TOKEN, expectedCredential.accessToken)
                putString(Constants.REFRESH_TOKEN, expectedCredential.refreshToken)
            }
            .commit()

        subject.removeCredential()

        assertFalse { credentialLocalSource.contains(Constants.ACCESS_TOKEN) }
        assertFalse { credentialLocalSource.contains(Constants.REFRESH_TOKEN) }

        credentialLocalSource
            .edit()
            .clear()
            .commit()
    }
}


