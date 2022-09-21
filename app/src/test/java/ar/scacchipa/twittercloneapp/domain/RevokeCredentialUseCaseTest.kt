package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.domain.usecase.RevokeCredentialUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class RevokeCredentialUseCaseTest {

    private val mockCredentialRepository = mockk<ICredentialRepository>()

    private val subject = RevokeCredentialUseCase(
        credentialRepository = mockCredentialRepository
    )

    @Test
    fun subjectIsInvokedTest() = runTest {
        coEvery {
            mockCredentialRepository.revokeCredential()
        } returns true

        subject.invoke()

        coVerify {
            mockCredentialRepository.revokeCredential()
        }
    }
}