package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import org.junit.Assert
import org.junit.Test

class CheckCredentialCaseTest {

    private var mockCredentialRepo = MockCredentialRepo(
        MockTokenProvider.credential1()
    )
    private val subject = CheckCredentialUseCase(
        credentialRepository = mockCredentialRepo
    )

    @Test
    fun subjectFindCredential() {
        Assert.assertTrue(
            subject()
        )
    }

    @Test
    fun subjectNotFindCredential() {
        mockCredentialRepo.credential = MockTokenProvider.credentialNull()

        Assert.assertFalse(
            subject()
        )

        mockCredentialRepo.credential = MockTokenProvider.credential1()
    }

    class MockCredentialRepo(
        var credential: Credential
    ): ICredentialRepository {
        override suspend fun storeCredential(credential: Credential): Boolean {
            throw NotImplementedError("Not yet implemented")
        }

        override fun recoverCredential(): Credential {
            return credential
        }

        override suspend fun removeCredential() {
            throw NotImplementedError("Not yet implemented")
        }
    }
}

