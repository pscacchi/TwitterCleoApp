package ar.scacchipa.twittercloneapp.presentation.starter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.domain.usecase.SplashTimerUseCase
import ar.scacchipa.twittercloneapp.presentation.splash.SplashViewModel
import ar.scacchipa.twittercloneapp.utils.MainCoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    var mainCoroutineTestRule = MainCoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun checkSplashIsSpent() = runTest {
            val viewModel = SplashViewModel(SplashTimerMock())
            Assert.assertEquals(false, viewModel.splashWasSpent.value)
            viewModel.spendSplash()
            Assert.assertEquals(true, viewModel.splashWasSpent.value)
    }
}

class SplashTimerMock: SplashTimerUseCase() {
    override suspend fun spendSplash(): Boolean {
        return true
    }
}