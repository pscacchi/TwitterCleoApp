package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.data.repository.CredentialRepository
import ar.scacchipa.twittercloneapp.domain.model.ResponseDomain
import ar.scacchipa.twittercloneapp.domain.usecase.AuthorizationUseCase
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthorizationUseCaseTest {

    private val mockCredentialRepository = mockk<CredentialRepository>()

    private val authorizationUseCase = AuthorizationUseCase(
        credentialRepository = mockCredentialRepository
    )

    @Test
    fun authorizationUseCaseReturnsASuccessCredential() = runTest {

        coEvery {
            mockCredentialRepository.getExternalCredential(
                transitoryToken = MockTokenProvider.transitoryToken1(),
                grantType = Constants.GRANT_TYPE_AUTHORIZATION_CODE,
                clientId = Constants.CLIENT_ID,
                redirectUri = Constants.REDIRECT_URI,
                codeVerifier = Constants.CODE_VERIFIER_CHALLENGE,
                state = Constants.STATE_STATE
            )
        } returns( ResponseDomain.Success(MockTokenProvider.credential1()) )

        var wasStored = false
        coEvery {
            mockCredentialRepository.storeLocalCredential( MockTokenProvider.credential1() )
        } coAnswers {
            wasStored = true
            true
        }

        Assert.assertEquals(
            ResponseDomain.Success(
                MockTokenProvider.credential1()
            ),
            authorizationUseCase( MockTokenProvider.transitoryToken1() )
        )

        Assert.assertTrue(wasStored)
    }

    @Test
    fun authorizationUseCaseReturnsAError() = runTest {

        coEvery {
            mockCredentialRepository.getExternalCredential(
                transitoryToken = MockTokenProvider.incorrectTransitoryToken(),
                grantType = Constants.GRANT_TYPE_AUTHORIZATION_CODE,
                clientId = Constants.CLIENT_ID,
                redirectUri = Constants.REDIRECT_URI,
                codeVerifier = Constants.CODE_VERIFIER_CHALLENGE,
                state = Constants.STATE_STATE )
        } returns( ResponseDomain.Error() )

        var wasStored = false
        coEvery {
            mockCredentialRepository.storeLocalCredential(MockTokenProvider.credential1())
        } coAnswers {
            wasStored = false
            false
        }

        Assert.assertEquals(
            ResponseDomain.Error(),
            authorizationUseCase( MockTokenProvider.incorrectTransitoryToken() )
        )
        Assert.assertFalse(wasStored)
    }
}
