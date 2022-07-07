package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.datasource.IAuthDataSource
import ar.scacchipa.twittercloneapp.datasource.UserAccessToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class AuthorizationRepositoryTest {

    private var authorizationRepository: IAuthorizationRepository? = null

    @Before
    fun setup() {
        authorizationRepository = AuthorizationRepository(MockAuthDataSource())
    }

    @Test
    fun shouldReturnACorrectUserAccessToken() = runTest {
        val expected = UserAccessToken(
            tokenType = "bearer",
            expiresIn = 7200,
            accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
            scope = "tweet.read tweet.write",
            refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
            error = "",
            errorDescription = ""
        )
        val actual = authorizationRepository?.genAccessToken(
            transitoryToken = "SGVvLWIyclkweEJudVZWSFFyR3RqQUVadEdlSFZJRk1JLXRacllVb3BxRFhhOjE2NTcxMTQyMDA2ODY6MTowOmFjOjE",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state")
        Assert.assertEquals(expected, actual)
    }
    @Test
    fun shouldReturnAErrorUserAccessToken() = runTest {
        val actual = authorizationRepository?.genAccessToken(
            transitoryToken = "incorrect_password",
            grantType = "authorization_code",
            clientId = "Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ",
            redirectUri = "https://twittercloneendava.firebaseapp.com/__/auth/handler&",
            codeVerifier = "challenge",
            state = "state")
        val expected = UserAccessToken(error = "error")

        Assert.assertEquals(expected, actual)
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
                    error = "",
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