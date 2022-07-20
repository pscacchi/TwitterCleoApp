package ar.scacchipa.twittercloneapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.domain.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.domain.ConsumableAuthUseCase
import ar.scacchipa.twittercloneapp.repository.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.net.URI

@ExperimentalCoroutinesApi
class AuthWebDialogViewModelTest {
    private var subject: AuthWebDialogViewModel = AuthWebDialogViewModel(
        authorizationUseCase = MockAuthorizationUseCase(),
        consumableAuthUseCase = MockConsumableAuthUseCase()
    )

    @get:Rule
    var mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun viewModelShouldGenerateCodeUrl() {


        Assert.assertEquals(
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
    fun viewModelShouldNotGenerateUserAccessToken() {
        val uri = URI("https://mobile.twitter.com/i/oauth2/authorize?response_type=code&" +
                "client_id=Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ&" +
                "redirect_uri=https://twittercloneendava.firebaseapp.com/__/auth/handler&" +
                "scope=tweet.read%20tweet.write" +
                "state=state&code_challenge=challenge&code_challenge_method=plain")


        Assert.assertEquals(subject.userAccessToken.value, null)
    }

    @Test
    fun shouldGenerateSuccessUserAccessToken() = runTest {
        val uri = URI("https://twittercloneendava.firebaseapp.com/__/auth/handler?state=state&" +
                "code=SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE")

        subject.onReceiveUrl(uri)

        Assert.assertTrue(
            subject.userAccessToken.value ==
            UserAccessToken(
                tokenType = "bearer",
                expiresIn = 7200,
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                scope = "tweet.read tweet.write",
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
                error = "",
                errorDescription = ""))
    }

    @Test
    fun viewModelShouldGenerateCancelledNoAuthUserAccessToken() {
        val uri = URI("https://twittercloneendava.firebaseapp.com/__/auth/handler?error=access_denied&state=state")

        subject.onReceiveUrl(uri)

        Assert.assertTrue(
            subject.userAccessToken.value?.error == Constants.ERROR_CANCELLED_AUTH)
    }
}

class MockAuthorizationUseCase: AuthorizationUseCase() {
    override suspend operator fun invoke(
        transitoryToken: String
    ): UserAccessToken {
        return if (transitoryToken == "SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE") {
            UserAccessToken(
                tokenType = "bearer",
                expiresIn = 7200,
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                scope = "tweet.read tweet.write",
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
                error = "",
                errorDescription = ""
            )
        } else {
            UserAccessToken(error = "error")
        }
    }
}

class MockConsumableAuthUseCase: ConsumableAuthUseCase() {
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