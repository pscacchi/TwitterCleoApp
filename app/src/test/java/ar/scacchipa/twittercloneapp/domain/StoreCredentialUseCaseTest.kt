package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.Credential
import ar.scacchipa.twittercloneapp.repository.ICredentialLocalRepository
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class StoreCredentialUseCaseTest {

    private val subject = StoreCredentialUseCase(
        credentialRepository = MockCredentialLocalRepo()
    )

    @Test
    fun saveTokenOnSharedPrefs() = runTest {
        val token = MockTokenProvider.credential1()

        assertTrue { subject.invoke(token) }
    }

    class MockCredentialLocalRepo: ICredentialLocalRepository {
        override suspend fun storeCredential(credential: Credential): Boolean {
            if (credential != MockTokenProvider.credential1()) {
                throw NotImplementedError("Not yet implemented")
            } else {
                return true
            }
        }

        override fun recoverCredential(): Credential {
            throw NotImplementedError("Not yet implemented")
        }

        override suspend fun removeCredential() {
            throw NotImplementedError("Not yet implemented")
        }
    }
}