package ar.scacchipa.twittercloneapp.data.repository

import android.content.SharedPreferences
import ar.scacchipa.twittercloneapp.data.UserAccessTokenMapper
import ar.scacchipa.twittercloneapp.data.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class CredentialRepositoryTest {

    private val mockCredentialLocalSource = mockk<SharedPreferences>()
    private val mockSharedEditor = mockk<SharedPreferences.Editor>()
    private val mockAccessTokenExternalSource = mockk<IAuthDataSource>()
    private val hashMap = mutableMapOf<String, String>()

    private val subject = CredentialRepository(
        credentialLocalSource = mockCredentialLocalSource,
        accessTokenExternalSource = mockAccessTokenExternalSource,
        mapper = UserAccessTokenMapper(),
        dispatcherDefault = Dispatchers.Default,
        dispatcherIO = Dispatchers.Default
    )

    @Before
    fun setup() {
        every {
            mockCredentialLocalSource.contains(any())
        } answers {
            hashMap.contains(firstArg())
        }

        every {
            mockCredentialLocalSource.getString(any(), "")
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
            mockCredentialLocalSource.edit()
        } returns mockSharedEditor

        coEvery {
            mockSharedEditor.commit()
        } returns true

        coEvery {
            mockAccessTokenExternalSource.genAccessTokenSourceCode(
                transitoryToken = MockTokenProvider.transitoryToken1(),
                grantType = "authorization_code",
                clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
                redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
                codeVerifier = "challenge",
                state = "state"
            )
        } returns Response.success(
            UserAccessToken(
                tokenType = "bearer",
                expiresIn = 7200,
                accessToken = MockTokenProvider.credential1().accessToken,
                scope = "tweet.read tweet.write",
                refreshToken = MockTokenProvider.credential1().refreshToken,
                errorDescription = ""
            )
        )

        coEvery {
            mockAccessTokenExternalSource.genAccessTokenSourceCode(
                "incorrect_password",
                grantType = any(),
                clientId = any(),
                redirectUri = any(),
                codeVerifier = any(),
                state = any()
            )
        } returns Response.error(
            401,
            ResponseBody.create(
                MediaType.parse("text/plain"), "Some error"
            )
        )

        coEvery {
            mockAccessTokenExternalSource.genAccessTokenSourceCode(
                transitoryToken = "request_without_body",
                grantType = any(),
                clientId = any(),
                redirectUri = any(),
                codeVerifier = any(),
                state = any()
            )
        } returns Response.success(null)

        coEvery {
            mockAccessTokenExternalSource.genAccessTokenSourceCode(
                transitoryToken = "throw_exception",
                grantType = any(),
                clientId = any(),
                redirectUri = any(),
                codeVerifier = any(),
                state = any()
            )
        } throws Exception()
    }

    @Test
    fun subjectRecoverStoredCredential() = runTest {
        val expectedCredential = MockTokenProvider.credential1()

        hashMap[Constants.ACCESS_TOKEN] = MockTokenProvider.credential1().accessToken
        hashMap[Constants.REFRESH_TOKEN] = MockTokenProvider.credential1().refreshToken

        val actualCredential = subject.recoverLocalCredential()

        assertEquals(
            expectedCredential,
            actualCredential
        )

        hashMap.clear()
    }

    @Test
    fun subjectLocalStoreCredential() = runTest {
        val expectedCredential = MockTokenProvider.credential1()

        assertTrue { subject.storeLocalCredential(expectedCredential) }

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

        val actualCredential = subject.recoverLocalCredential()

        assertEquals(
            expectedCredential,
            actualCredential
        )

        hashMap.clear()
    }

    @Test
    fun externalSourceReturnsASuccessToken() = runTest {
        val expected = ResponseDomain.Success( MockTokenProvider.credential1() )

        val actual = subject.getExternalCredential(
            transitoryToken = MockTokenProvider.transitoryToken1(),
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun externalSourceReturnsNoAuthToken() = runTest {
        val actual = subject.getExternalCredential(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state")
        val expected = ResponseDomain.Error(error = Constants.ERROR_HOST_LOOKUP_TOKEN)

        assertEquals(expected, actual)
    }

    @Test
    fun whenExternalSourceReceivesRequestWithoutBodyReturnError() = runTest {
        assertEquals(
            ResponseDomain.Error(),
            subject.getExternalCredential(
                transitoryToken = "request_without_body",
                grantType = "authorization_code",
                clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
                redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
                codeVerifier = "challenge",
                state = "state"
            )
        )
    }

    @Test
    fun whenExternalSourceCatchException() = runTest {
        assertEquals(
            ResponseDomain.Exception(),
            subject.getExternalCredential(
                transitoryToken = "throw_exception",
                grantType = "authorization_code",
                clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
                redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
                codeVerifier = "challenge",
                state = "state"
            ))
    }
}



