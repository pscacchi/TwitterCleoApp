package ar.scacchipa.twittercloneapp.presentation.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.domain.usecase.StarterUseCase
import ar.scacchipa.twittercloneapp.utils.MainCoroutineTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    var mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockStarterUseCase = mockk<StarterUseCase>()

    private val subject = SplashViewModel(
        starterUseCase = mockStarterUseCase
    )

    @Test
    fun subjectShouldGoToLogin() = runTest {
        coEvery {
            mockStarterUseCase.invoke()
        } returns SplashState.GoToLogin
        subject.spendSplash()
        coVerify {
            mockStarterUseCase()
        }
        assertTrue(
            subject.splashState.value == SplashState.GoToLogin
        )
    }

    @Test
    fun subjectShouldSkipLogin() = runTest {
        coEvery {
            mockStarterUseCase.invoke()
        } returns SplashState.SkipLogin
        subject.spendSplash()
        coVerify {
            mockStarterUseCase()
        }
        assertTrue(
            subject.splashState.value == SplashState.SkipLogin
        )
    }
}