package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import ar.scacchipa.twittercloneapp.repository.IAuthorizationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthorizationUseCaseTest {

    private val authorizationUseCase = AuthorizationUseCase(MockAuthorizationRespository())

    @Test
    fun authorizationUseCaseShouldReturnAUserAccessToken() = runTest {
        Assert.assertEquals(
            authorizationUseCase("SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE"),
            UserAccessToken(
                tokenType = "bearer",
                expiresIn = 7200,
                accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                scope = "tweet.read tweet.write",
                refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
                error = "",
                errorDescription = ""
            ))
    }
}

class MockAuthorizationRespository: IAuthorizationRepository {
    override suspend fun requestAccessToken(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): UserAccessToken {
        return UserAccessToken(
            tokenType = "bearer",
            expiresIn = 7200,
            accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
            scope = "tweet.read tweet.write",
            refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
            error = "",
            errorDescription = ""
        )
    }

    override fun getCancelledAuthToken(): UserAccessToken {
        return UserAccessToken(error = "cancelled_authorization")
    }

    override fun getErrorLookupToken(): UserAccessToken {
        return UserAccessToken(error = "no_authorization")
    }
}
