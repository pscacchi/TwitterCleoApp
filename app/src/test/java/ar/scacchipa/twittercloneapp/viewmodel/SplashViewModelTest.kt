package ar.scacchipa.twittercloneapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.domain.ICheckCredentialUseCase
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import ar.scacchipa.twittercloneapp.utils.MainCoroutineTestRule
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

    private val checkCredentialsUseCase = MockCheckCredentialUseCase()

    private val subject = SplashViewModel(
        MockSplashTimer(),
        checkCredentialsUseCase
    )

    @Test
    fun checkSplashIsSpent() = runTest {
        assertEquals(false, subject.splashWasSpent.value)
        subject.spendSplash()
        assertEquals(true, subject.splashWasSpent.value)
    }

    @Test
    fun checkCredentialsReturnTrue() {
        checkCredentialsUseCase
        assertTrue(
            subject.onCheckCredentials()
        )
    }

    class MockSplashTimer : SplashTimerUseCase() {
        override suspend fun spendSplash(): Boolean {
            return true
        }
    }

    class MockCheckCredentialUseCase : ICheckCredentialUseCase {
        override operator fun invoke(): Boolean {
            return true
        }
    }
}
