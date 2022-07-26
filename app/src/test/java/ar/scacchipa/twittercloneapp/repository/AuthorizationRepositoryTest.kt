package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class AuthorizationRepositoryTest {

    private var subject: IAuthorizationRepository = AuthorizationRepository(MockAuthDataSource())

    @Test
    fun repoShouldReturnASuccessToken() = runTest {
        val expected = TokenResource.Success(
            tokenType = "bearer",
            expiresIn = 7200,
            accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
            scope = "tweet.read tweet.write",
            refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
        )
        val actual = subject.requestAccessToken(
            transitoryToken = "SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state")
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun repoShouldReturnNoAuthToken() = runTest {
        val actual = subject.requestAccessToken(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state")
        val expected = TokenResource.Error(error = Constants.ERROR_HOST_LOOKUP_TOKEN)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun whenAuthRepoReceiveRequestWithoutBodyReturnError() = runTest {
        val mockAuthDataSource = Mockito.mock(IAuthDataSource::class.java)
        Mockito.`when`(mockAuthDataSource.genAccessTokenSourceCode(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state"
        )).thenReturn(
            Response.success(null)
        )
        val subject = AuthorizationRepository(mockAuthDataSource)

        Assert.assertEquals(TokenResource.Error(), subject.requestAccessToken(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state"
        ))
    }

    @Test
    fun whenAuthorizationRepoCatchException() = runTest {
        val subject = AuthorizationRepository(
            genAccessTokenSource = MockExceptionAuthDataSource()
        )

        Assert.assertEquals(TokenResource.Exception(), subject.requestAccessToken(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state"
        ))
    }
}

class MockAuthDataSource: IAuthDataSource {
    override suspend fun genAccessTokenSourceCode(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): Response<UserAccessToken> {
        if (transitoryToken == "SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE" &&
            grantType == "authorization_code" &&
            clientId == "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ" &&
            redirectUri == "https://twittercloneendava.firebaseapp.com/__/auth/handler&" &&
            codeVerifier == "challenge" &&
            state == "state"
        ) {
            return Response.success(
                UserAccessToken(
                    tokenType = "bearer",
                    expiresIn = 7200,
                    accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
                    scope = "tweet.read tweet.write",
                    refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
                    errorDescription = ""
                )
            )
        } else {
            return Response.error(
                401,
                ResponseBody.create(
                    MediaType.parse("text/plain"), "Some error"
                )
            )
        }
    }
}

class MockExceptionAuthDataSource: IAuthDataSource {
    override suspend fun genAccessTokenSourceCode(
        transitoryToken: String,
        grantType: String,
        clientId: String,
        redirectUri: String,
        codeVerifier: String,
        state: String
    ): Response<UserAccessToken> {
        throw Exception()
    }
}
