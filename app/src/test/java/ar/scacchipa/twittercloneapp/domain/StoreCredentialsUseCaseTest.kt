package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.repository.ICredentialRepository
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class StoreCredentialsUseCaseTest {

    private val subject = StoreCredentialUseCase(
        credentialRepository = MockCredentialRepo()
    )

    @Test
    fun saveTokenOnSharedPrefs() = runTest {
        val token = MockTokenProvider.credential1()

        assertTrue { subject.invoke(token) }
    }

    class MockCredentialRepo: ICredentialRepository {
        override suspend fun storeCredentials(credential: Credential): Boolean {
            if (credential != MockTokenProvider.credential1()) {
                throw NotImplementedError("Not yet implemented")
            } else {
                return true
            }
        }

        override fun recoverCredentials(): Credential {
            throw NotImplementedError("Not yet implemented")
        }

        override suspend fun removeCredential() {
            throw NotImplementedError("Not yet implemented")
        }
    }
}