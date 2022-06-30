package ar.scacchipa.twittercloneapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.scacchipa.twittercloneapp.domain.SplashTimerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun checkSplashIsSpent() = runTest {
            val viewModel = SplashViewModel(SplashTimerMock())
            Assert.assertEquals(false, viewModel.getSplashWasSpent())
            viewModel.spendSplash()
            Assert.assertEquals(true, viewModel.getSplashWasSpent())
    }
}

class SplashTimerMock: SplashTimerUseCase() {
    override suspend fun spendSplash(): Boolean {
        return true
    }
}

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}