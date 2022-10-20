package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.UserAccessTokenMapper
import ar.scacchipa.twittercloneapp.data.datasource.IAuthExternalSource
import ar.scacchipa.twittercloneapp.data.datasource.MockLocalStorage
import ar.scacchipa.twittercloneapp.data.model.RevokeData
import ar.scacchipa.twittercloneapp.data.model.UserAccessToken
import ar.scacchipa.twittercloneapp.domain.model.Credential
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class CredentialRepositoryTest {

    private var mockCredentialLocalSource = MockLocalStorage()
    private val mockAuthExternalSource = mockk<IAuthExternalSource>()
    private val hashMap = mutableMapOf<String, String>()

    private val subject = CredentialRepository(
        credentialLocalSource = mockCredentialLocalSource,
        authExternalSource = mockAuthExternalSource,
        mapper = UserAccessTokenMapper(),
        dispatcherDefault = Dispatchers.Default,
        dispatcherIO = Dispatchers.Default
    )

    @Before
    fun setup() {

        coEvery {
            mockAuthExternalSource.genAccessTokenSourceCode(
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
            mockAuthExternalSource.genAccessTokenSourceCode(
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
            mockAuthExternalSource.genAccessTokenSourceCode(
                transitoryToken = "request_without_body",
                grantType = any(),
                clientId = any(),
                redirectUri = any(),
                codeVerifier = any(),
                state = any()
            )
        } returns Response.success(null)

        coEvery {
            mockAuthExternalSource.genAccessTokenSourceCode(
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

        mockCredentialLocalSource.save(
            Constants.ACCESS_TOKEN,
            MockTokenProvider.credential1().accessToken
        )
        mockCredentialLocalSource.save(
            Constants.REFRESH_TOKEN,
            MockTokenProvider.credential1().refreshToken
        )

        val actualCredential = subject.recoverLocalCredential()

        assertEquals(
            expectedCredential,
            actualCredential
        )

        mockCredentialLocalSource = MockLocalStorage()
    }

    @Test
    fun subjectLocalStoresCredential() = runTest {
        val expectedCredential = MockTokenProvider.credential1()

        assertTrue ( subject.storeLocalCredential(expectedCredential) )

        val storedCredential = Credential(
            mockCredentialLocalSource.getString(Constants.ACCESS_TOKEN)?:"",
            mockCredentialLocalSource.getString(Constants.REFRESH_TOKEN)?:""
        )

        assertEquals(
            expectedCredential,
            storedCredential
        )

        mockCredentialLocalSource = MockLocalStorage()
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

    @Test
    fun subjectRevokeCredentialReturnsTrue() = runTest {

        mockCredentialLocalSource.save(
            Constants.ACCESS_TOKEN,
            MockTokenProvider.credential1().accessToken
        )

        coEvery {
            mockAuthExternalSource.revokeToken(
                token = MockTokenProvider.credential1().accessToken,
                clientId = Constants.CLIENT_ID,
                tokenTypeHint = Constants.ACCESS_TOKEN
            )
        } returns Response.success(
            RevokeData(true)
        )

        subject.revokeCredential()

        coVerify {
            mockAuthExternalSource.revokeToken(
                token = MockTokenProvider.credential1().accessToken,
                clientId = Constants.CLIENT_ID,
                tokenTypeHint = Constants.ACCESS_TOKEN
            )
            mockCredentialLocalSource.remove(Constants.ACCESS_TOKEN)
            mockCredentialLocalSource.remove(Constants.REFRESH_TOKEN)
        }

        mockCredentialLocalSource = MockLocalStorage()
    }

    @Test
    fun subjectRevokeCredentialReturnsFalse() = runTest {

        mockCredentialLocalSource.save(
            Constants.ACCESS_TOKEN,
            MockTokenProvider.credential1().accessToken
        )

        coEvery {
            mockAuthExternalSource.revokeToken(
                token = MockTokenProvider.credential1().accessToken,
                clientId = Constants.CLIENT_ID,
                tokenTypeHint = Constants.ACCESS_TOKEN
            )
        } returns Response.success(
            RevokeData(false)
        )

        subject.revokeCredential()

        coVerify {
            mockAuthExternalSource.revokeToken(
                token = MockTokenProvider.credential1().accessToken,
                clientId = Constants.CLIENT_ID,
                tokenTypeHint = Constants.ACCESS_TOKEN
            )
            mockCredentialLocalSource.remove(Constants.ACCESS_TOKEN)
            mockCredentialLocalSource.remove(Constants.REFRESH_TOKEN)
        }

        mockCredentialLocalSource = MockLocalStorage()
    }
}