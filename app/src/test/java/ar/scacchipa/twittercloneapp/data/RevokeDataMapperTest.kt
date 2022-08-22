package ar.scacchipa.twittercloneapp.data

import org.junit.Assert
import org.junit.Test

class RevokeDataMapperTest {

    private val subject = RevokeDataMapper()

    @Test
    fun mapFromDataToDomain() {
        val expected = RevokeDomain(true)
        val actual = subject.toDomain(RevokeData(true))

        Assert.assertEquals( expected, actual )
    }

    @Test
    fun mapFromDomainToData() {
        val expected = RevokeData(true)
        val actual = subject.fromDomain(RevokeDomain(true))

        Assert.assertEquals( expected, actual )
    }
}
