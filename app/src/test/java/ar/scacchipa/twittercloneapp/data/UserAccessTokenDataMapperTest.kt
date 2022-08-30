package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.datasource.UserAccessTokenData
import ar.scacchipa.twittercloneapp.data.repository.UserAccessTokenDataMapper
import ar.scacchipa.twittercloneapp.data.repository.UserAccessTokenDomain
import org.junit.Assert
import org.junit.Test


class UserAccessTokenDataMapperTest {

    val subject = UserAccessTokenDataMapper()

    @Test
    fun subjectReturnUserAccessDomain() {
        val expected = mockUserAccessTokenDomain()
        val actual = subject.toDomain( mockUserAccessTokenData() )
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun subjectReturnUserAccessData() {
        val expected = subject.fromDomain( mockUserAccessTokenDomain() )
        val actual = mockUserAccessTokenData()
        Assert.assertEquals(expected, actual)
    }


    private fun mockUserAccessTokenDomain(): UserAccessTokenDomain {
        return UserAccessTokenDomain(
            tokenType = "bearer",
            expiresIn = 7200,
            accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
            scope = "tweet.read tweet.write",
            refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE")
    }
    private fun mockUserAccessTokenData(): UserAccessTokenData {
        return UserAccessTokenData(
            tokenType = "bearer",
            expiresIn = 7200,
            accessToken = "OU1tZ2dUanRYMjhGUEVnOUlHUGlYUUlyWVI3Ukhpd1gweW9ET051OW9HR2hTOjE2NTY1OTUxOTIxMTU6MToxOmF0OjE",
            scope = "tweet.read tweet.write",
            refreshToken = "LVJQQXMxSUM0QUQ2eHNidkNfYUNScUJoSTY5Sy1ndGxqMmx2WnRPQzF4NklDOjE2NTY1OTUxOTIxMTU6MTowOnJ0OjE",
            errorDescription = "")
    }
}