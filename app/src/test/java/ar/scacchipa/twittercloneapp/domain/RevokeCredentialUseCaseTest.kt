package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.data.RevokeDomain
import ar.scacchipa.twittercloneapp.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.repository.IRevokeTokenRepository
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class RevokeCredentialUseCaseTest {

    private val mockRepository = MockRevokeRepository()
    private val mockCredentialRepo = MockCredentialRepo()

    val subject = RevokeCredentialUseCase(
        repository = mockRepository,
        credentialLocalRepo = mockCredentialRepo
    )

    @Test
    fun subjectRevokeFromLocalAndRepo() = runTest {
        subject(
            MockTokenProvider.credential1().accessToken,
            Constants.CLIENT_ID
        )
        assertTrue(mockRepository.revoked)
        assertTrue(mockCredentialRepo.deleted)
    }

    class MockRevokeRepository(
        var revoked: Boolean = false
    ): IRevokeTokenRepository {
        override suspend fun revokeToken(token: String, clientId: String): ResponseDomain {

            if (MockTokenProvider.credential1().accessToken == token
                && clientId == Constants.CLIENT_ID) {
                revoked = true
            }
            return ResponseDomain.Success(RevokeDomain(true))
        }
    }
    class MockCredentialRepo(
        var deleted: Boolean = false
    ): ICredentialRepository {

        override suspend fun storeCredential(credential: Credential): Boolean {
            throw NotImplementedError("Not yet implemented")
        }

        override fun recoverCredential(): Credential {
            throw NotImplementedError("Not yet implemented")
        }

        override suspend fun removeCredential() {
            deleted = true
        }
    }
}