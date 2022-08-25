package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.data.RevokeDomain
import ar.scacchipa.twittercloneapp.repository.ICredentialLocalRepository
import ar.scacchipa.twittercloneapp.repository.IRevokeTokenRepository
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class LogOutUseCaseTest {

    private val mockRepository = MockRevokeRepository()
    private val mockCredentialRepo = MockCredentialLocalRepo()

    val subject = RevokeCredentialUseCase(
        repository = mockRepository,
        credentialLocalRepo = mockCredentialRepo
    )

    @Test
    fun subjectRevokeFromLocalAndRepo() = runTest {
        subject()
        assertTrue(mockRepository.revoked)
        assertTrue(mockCredentialRepo.deleted)
    }
    @Test
    fun subjectNotRevokeFromLocalAndRepo() = runTest {
        mockCredentialRepo.correctCredential = false

        subject()
        assertFalse(mockRepository.revoked)
        assertFalse(mockCredentialRepo.deleted)

        mockCredentialRepo.correctCredential= true
    }

    class MockRevokeRepository(
        var revoked: Boolean = false
    ): IRevokeTokenRepository {
        override suspend fun revokeToken(token: String, clientId: String): ResponseDomain {

            if (MockTokenProvider.credential1().accessToken == token
                && clientId == Constants.CLIENT_ID) {
                revoked = true
                return ResponseDomain.Success(RevokeDomain(true))
            }
            return ResponseDomain.Error("", "")
        }
    }
    class MockCredentialLocalRepo(
        var deleted: Boolean = false,
        var correctCredential: Boolean = true
    ): ICredentialLocalRepository {

        override suspend fun storeCredential(credential: Credential): Boolean {
            throw NotImplementedError("Not yet implemented")
        }

        override fun recoverCredential(): Credential {
            return if (correctCredential) MockTokenProvider.credential1()
            else Credential("", "")
        }

        override suspend fun removeCredential() {
            deleted = true
        }
    }
}