package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.domain.usecase.SplashTimerUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashTimerUseCaseUnitTest {
    @Test
    fun returnTrueFiveSecondLater() = runTest {
        assertEquals(true, SplashTimerUseCase().spendSplash())
        assertEquals(5000, this.testScheduler.currentTime )
    }
}