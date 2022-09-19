package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.repository.CredentialRepository
import ar.scacchipa.twittercloneapp.domain.usecase.StarterUseCase
import ar.scacchipa.twittercloneapp.presentation.splash.SplashState
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashTimerUseCaseUnitTest {

    private val mockCredentialRepository = mockk<CredentialRepository>()

    private val subject = StarterUseCase(
        credentialRepository = mockCredentialRepository
    )

    @Test
    fun subjectReturnsGoToLogin() = runTest {
        coEvery {
            mockCredentialRepository.recoverLocalCredential()
        } returns  null

        assertEquals(SplashState.GoToLogin, subject())
        assertEquals(5000, this.testScheduler.currentTime )
    }

    @Test
    fun subjectReturnsSkipLoginFalse() = runTest {
        coEvery {
            mockCredentialRepository.recoverLocalCredential()
        } returns ( MockTokenProvider.credential1() )

        assertEquals(SplashState.SkipLogin, subject())
        assertEquals(5000, this.testScheduler.currentTime )
    }
}