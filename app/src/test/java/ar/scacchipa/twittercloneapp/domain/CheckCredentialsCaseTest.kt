package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import org.junit.Assert
import org.junit.Test

class CheckCredentialsCaseTest {

    private var mockCredentialRepo = MockCredentialRepo(
        MockTokenProvider.credential1()
    )
    private val subject = CheckCredentialUseCase(
        credentialRepository = mockCredentialRepo
    )

    @Test
    fun subjectFindCredentials() {
        Assert.assertTrue(
            subject()
        )
    }

    @Test
    fun subjectNotFindCredentials() {
        mockCredentialRepo.credential = MockTokenProvider.credentialNull()

        Assert.assertFalse(
            subject()
        )

        mockCredentialRepo.credential = MockTokenProvider.credential1()
    }

    class MockCredentialRepo(
        var credential: Credential
    ): ICredentialRepository {
        override suspend fun storeCredentials(credential: Credential): Boolean {
            throw NotImplementedError("Not yet implemented")
        }

        override fun recoverCredentials(): Credential {
            return credential
        }

        override suspend fun removeCredential() {
            throw NotImplementedError("Not yet implemented")
        }
    }
}

