package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository
import org.junit.Assert
import org.junit.Test

class ErrorTokenCreatorUseCaseTest {
    val subject = ErrorTokenCreatorUseCase(MockAuthorizationRepository())

    @Test
    fun subjectShouldReturnAErrorUserTokenCreator() {
        Assert.assertEquals(
            UserAccessToken(error = "error"),
            subject())
    }
}

class MockAuthorizationRepository: IAuthorizationRepository {
    override suspend fun genAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): UserAccessToken {
        TODO("Not yet implemented")
    }

    override fun getErrorUserCaseTokenCreator(): UserAccessToken {
        return UserAccessToken(error= "error")
    }

}