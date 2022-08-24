package ar.scacchipa.twittercloneapp.viewmodel

import android.webkit.WebResourceError
import android.webkit.WebViewClient
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.ConsumableAuthUseCase
import ar.scacchipa.twittercloneapp.domain.IStoreCredentialUseCase
import ar.scacchipa.twittercloneapp.domain.MockAuthorizationRepository
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MainCoroutineTestRule
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.net.URI

@ExperimentalCoroutinesApi
class AuthWebDialogViewModelTest {
    private var subject: AuthWebDialogViewModel = AuthWebDialogViewModel(
        authorizationUseCase = MockAuthorizationUseCase(),
        consumableAuthUseCase = MockConsumableAuthUseCase(),
        storeCredentialUseCase = MockStoreCredentialUseCase()
    )

    @get:Rule
    var mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun viewModelShouldGenerateCodeUrl() {
        assertEquals(
            subject.createTemporaryCodeUrl(),
            "https://twitter.com/i/oauth2/authorize?" +
                    "response_type=code&" +
                    "client_id=Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ&" +
                    "redirect_uri=https://twittercloneendava.firebaseapp.com/__/auth/handler&" +
                    "scope=tweet.read%20tweet.write&" +
                    "state=state&" +
                    "code_challenge=challenge&" +
                    "code_challenge_method=plain"
        )
    }

    @Test
    fun shouldGenerateSuccessTokenResource() = runTest {
        val uri = URI(
            "https://twittercloneendava.firebaseapp.com/__/auth/handler?state=state&" +
                    "code=SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE"
        )

        subject.onReceiveUrl(uri)

        Assert.assertTrue(
            subject.responseDomain.value ==
                    ResponseDomain.Success(
                        Credential(
                            accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                            refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE"
                        )
                    )
        )
    }

    @Test
    fun viewModelShouldGenerateCancelTokenResource() {
        val uri =
            URI("https://twittercloneendava.firebaseapp.com/__/auth/handler?error=access_denied&state=state")

        subject.onReceiveUrl(uri)

        Assert.assertTrue(
            subject.responseDomain.value == ResponseDomain.Cancel
        )
    }

    @Test
    fun webViewSendWebResourceErrorToSubject() {
        val mockedWebResourceError = Mockito.mock(WebResourceError::class.java)
        Mockito.`when`(mockedWebResourceError.errorCode).thenReturn(WebViewClient.ERROR_HOST_LOOKUP)
        subject.onReceivedWebError(mockedWebResourceError)
        assertEquals(
            ResponseDomain.Error(error = Constants.ERROR_HOST_LOOKUP_TOKEN),
            subject.responseDomain.value
        )
    }

    @Test
    fun onSaveAccessTokenStoresInLiveData() {
        subject.onLocalStoreCredential( MockTokenProvider.credential1() )

        assertEquals(
            MockTokenProvider.credential1(),
            subject.savedCredential.value
        )
    }

    class MockAuthorizationUseCase : AuthorizationUseCase(MockAuthorizationRepository()) {
        override suspend operator fun invoke(
            transitoryToken: String
        ): ResponseDomain {
            return if (transitoryToken == "SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE") {
                ResponseDomain.Success(
                    MockTokenProvider.credential1()
                )
            } else {
                ResponseDomain.Error(error = "error")
            }
        }
    }

    class MockConsumableAuthUseCase : ConsumableAuthUseCase() {
        override operator fun invoke(): String {
            return "https://twitter.com/i/oauth2/authorize?" +
                    "response_type=code&" +
                    "client_id=Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ&" +
                    "redirect_uri=https://twittercloneendava.firebaseapp.com/__/auth/handler&" +
                    "scope=tweet.read%20tweet.write&" +
                    "state=state&" +
                    "code_challenge=challenge&" +
                    "code_challenge_method=plain"
        }
    }

    class MockStoreCredentialUseCase : IStoreCredentialUseCase {
        override suspend fun invoke(credential: Credential): Boolean {
            return credential == MockTokenProvider.credential1()
        }
    }
}