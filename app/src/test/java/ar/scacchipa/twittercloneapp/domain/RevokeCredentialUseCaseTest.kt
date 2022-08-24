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

    val subject = RevokeCredentialsUseCase(
        repository = mockRepository,
        credentialRepo = mockCredentialRepo
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

        override suspend fun storeCredentials(credential: Credential): Boolean {
            throw NotImplementedError("Not yet implemented")
        }

        override fun recoverCredentials(): Credential {
            throw NotImplementedError("Not yet implemented")
        }

        override suspend fun removeCredential() {
            deleted = true
        }
    }
}


class RevokeCredentialsUseCase(
    private val repository: IRevokeTokenRepository,
    private val credentialRepo: ICredentialRepository
) {
    suspend operator fun invoke(
        token: String,
        clientId: String
    ) {
        val response = repository.revokeToken(
            token = token,
            clientId = clientId
        )
        when (response) {
            is ResponseDomain.Success<*> -> {
                val revokeDomain = response.data as RevokeDomain

                if (revokeDomain.revoked) {
                    credentialRepo.removeCredential()
                }
            }
            else ->
                return
        }
    }
}