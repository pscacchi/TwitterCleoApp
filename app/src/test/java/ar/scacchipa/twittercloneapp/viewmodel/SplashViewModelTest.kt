package ar.scacchipa.twittercloneapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.data.UserAccessTokenDomain
import ar.scacchipa.twittercloneapp.domain.IRecoveredAccessTokenUseCase
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import ar.scacchipa.twittercloneapp.utils.MainCoroutineTestRule
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    var mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val subject = SplashViewModel(
        MockSplashTimer(),
        MockRecoveredAccessTokenUseCase()
    )

    @Test
    fun checkSplashIsSpent() = runTest {
        assertEquals(false, subject.splashWasSpent.value)
        subject.spendSplash()
        assertEquals(true, subject.splashWasSpent.value)
    }

    @Test
    fun recoverUserAccessToken() {
        assertTrue(
            MockTokenProvider.userAccessTokenDomain1() == subject.onRecoverUserAccessToken()
        )
    }

    class MockSplashTimer : SplashTimerUseCase() {
        override suspend fun spendSplash(): Boolean {
            return true
        }
    }

    class MockRecoveredAccessTokenUseCase() : IRecoveredAccessTokenUseCase {
        override operator fun invoke(): UserAccessTokenDomain {
            return MockTokenProvider.userAccessTokenDomain1()
        }
    }
}
