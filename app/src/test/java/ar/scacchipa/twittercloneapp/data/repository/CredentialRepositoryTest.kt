package ar.scacchipa.twittercloneapp.data.repository

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class CredentialRepositoryTest {

    private val mockCredentialSource = mockk<SharedPreferences>()
    private val hashMap = mutableMapOf<String, String>()
    private val mockSharedEditor = mockk<SharedPreferences.Editor>()

    private val subject = CredentialRepository(
        credentialLocalSource = mockCredentialSource,
        dispatcher = Dispatchers.Default
    )

    @Before
    fun setup() {
        every {
            mockCredentialSource.contains(any())
        } answers {
            hashMap.contains(firstArg())
        }

        every {
            mockCredentialSource.getString(any(), "")
        } answers {
            hashMap[firstArg()]
        }

        coEvery {
            mockSharedEditor.putString(any(), any())
        } answers {
            hashMap[firstArg()] = secondArg()
            mockSharedEditor
        }

        coEvery {
            mockCredentialSource.edit()
        } returns mockSharedEditor

        coEvery {
            mockSharedEditor.commit()
        } returns true
    }

    @Test
    fun subjectRecoverStoredCredential() {
        val expectedCredential = MockTokenProvider.credential1()

        hashMap[Constants.ACCESS_TOKEN] = MockTokenProvider.credential1().accessToken
        hashMap[Constants.REFRESH_TOKEN] = MockTokenProvider.credential1().refreshToken

        val actualCredential = subject.recoverCredential()

        assertEquals(
            expectedCredential,
            actualCredential
        )

        hashMap.clear()
    }

    @Test
    fun subjectLocalStoreCredential() = runTest {
        val expectedCredential = MockTokenProvider.credential1()

        assertTrue { subject.storeCredential(expectedCredential) }

        val storedCredential = Credential(
            hashMap[Constants.ACCESS_TOKEN]?:"",
            hashMap[Constants.REFRESH_TOKEN]?:""
        )

        assertEquals(
            expectedCredential,
            storedCredential
        )

    }

    @Test
    fun getEmptyCredential() = runTest {
        val expectedCredential = null

        val actualCredential = subject.recoverCredential()

        assertEquals(
            expectedCredential,
            actualCredential
        )

        hashMap.clear()
    }
}


