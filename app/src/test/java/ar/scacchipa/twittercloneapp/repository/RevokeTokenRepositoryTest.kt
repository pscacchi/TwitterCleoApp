package ar.scacchipa.twittercloneapp.repository

import ar.scacchipa.twittercloneapp.data.ResponseDomain
import ar.scacchipa.twittercloneapp.data.RevokeData
import ar.scacchipa.twittercloneapp.data.RevokeDataMapper
import ar.scacchipa.twittercloneapp.data.RevokeDomain
import ar.scacchipa.twittercloneapp.datasource.IRevokeTokenDataSource
import ar.scacchipa.twittercloneapp.utils.Constants
import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RevokeTokenRepositoryTest {

    private val subject = RevokeTokenRepository(
        revokeTokenDataSource = MockRevokeTokenDataSource(),
        mapper = RevokeDataMapper(),
        dispatcher = Dispatchers.IO
    )

    @Test
    fun subjectReturnsRevokeTrue() = runTest {
        val response = subject.revokeToken(
            MockTokenProvider.userAccessTokenDomain1().accessToken,
            Constants.CLIENT_ID
        )
        Assert.assertTrue(
            ((response as ResponseDomain.Success<*>)
                .data as RevokeDomain).revoked)
    }

    @Test
    fun subjectReturnsRevokeError() = runTest {
        val response = subject.revokeToken(
            "WRONG_KEY",
            Constants.CLIENT_ID
        )
        Assert.assertTrue(response is ResponseDomain.Error)
    }
    @Test
    fun subjectReceivesResponseSuccessWithoutBody() = runTest {
        val response = subject.revokeToken(
            "-1",
            Constants.CLIENT_ID
        )
        Assert.assertTrue(response is ResponseDomain.Error)
    }
}

class MockRevokeTokenDataSource(): IRevokeTokenDataSource {
    override suspend fun revokeToken(
        token: String,
        clientId: String,
        tokenTypeHint: String
    ): Response<RevokeData> {
        val expectedToken = MockTokenProvider.userAccessTokenDomain1()
        return if (expectedToken.accessToken == token
            && clientId == Constants.CLIENT_ID
            && tokenTypeHint == Constants.TOKEN_TYPE_HINT) {
            Response.success(RevokeData(true))
        } else if (token == "-1") {
            Response.success(null)
        } else {
            Response.error(
                401,
                ResponseBody.create(
                    MediaType.parse("text/plain"), "Some error"
                )
            )
        }
    }
}