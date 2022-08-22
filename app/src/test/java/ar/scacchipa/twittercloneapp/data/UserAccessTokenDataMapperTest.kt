package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.utils.MockTokenProvider
import org.junit.Assert
import org.junit.Test


class UserAccessTokenDataMapperTest {

    private val subject = UserAccessTokenDataMapper()

    @Test
    fun subjectReturnUserAccessDomain() {
        val expected = MockTokenProvider.userAccessTokenDomain1()
        val actual = subject.toDomain( MockTokenProvider.userAccessTokenData1() )
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun subjectReturnUserAccessData() {
        val expected = subject.fromDomain( MockTokenProvider.userAccessTokenDomain1() )
        val actual = MockTokenProvider.userAccessTokenData1()
        Assert.assertEquals(expected, actual)
    }
}