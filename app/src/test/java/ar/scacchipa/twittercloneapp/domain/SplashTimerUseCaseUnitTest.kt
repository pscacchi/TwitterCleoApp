package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.repository.CredentialRepository
import ar.scacchipa.twittercloneapp.domain.usecase.StarterInUseCase
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashTimerUseCaseUnitTest {

    private val mockCredentialRepository = mockk<CredentialRepository>()

    private val subject = StarterInUseCase(
        credentialRepository = mockCredentialRepository
    )

    @Test
    fun subjectReturnTrue() = runTest {
        every {
            mockCredentialRepository.recoverLocalCredential()
        } returns null

        assertEquals(true, subject())
        assertEquals(5000, this.testScheduler.currentTime )
    }

    @Test
    fun subjectReturnFalse() = runTest {
        every {
            mockCredentialRepository.recoverLocalCredential()
        } returns ( MockTokenProvider.credential1() )
        assertEquals(false, subject())
        assertEquals(5000, this.testScheduler.currentTime )
    }
}