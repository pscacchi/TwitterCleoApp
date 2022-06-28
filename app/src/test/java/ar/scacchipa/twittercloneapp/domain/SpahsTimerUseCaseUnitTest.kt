package ar.scacchipa.twittercloneapp.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class SpahsTimerUseCaseUnitTest {
    @Test
    fun returnTrueFiveSecondLater() = runTest {
        var wasSpent = false
        val timer = SplashTimerUseCase()
        assertEquals(false, wasSpent)
        this.runCurrent()
        val initialTime = this.testScheduler.currentTime
        wasSpent = timer.spendSplash()

        val changeTime = this.testScheduler.currentTime

        assertEquals(5000, changeTime - initialTime)
        assertEquals(true, wasSpent)
        this.advanceUntilIdle()
        assertEquals(true, wasSpent)
    }
}