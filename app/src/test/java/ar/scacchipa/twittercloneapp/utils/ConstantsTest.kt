package ar.scacchipa.twittercloneapp.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ConstantsTest {

    @Test
    fun constant() {
        assertEquals("Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ", Constants.CLIENT_ID,)
        assertEquals("https://twittercloneendava.firebaseapp.com/__/auth/handler", Constants.REDIRECT_URI,)

        assertEquals("code", Constants.RESPONSE_TYPE_CODE)
        assertEquals("state", Constants.STATE)
        assertEquals("challenge", Constants.CODE_CHALLENGE_CHALLENGE)
        assertEquals("plain", Constants.CODE_CHALLENGE_METHOD_PLAIN)

        assertEquals("authorization_code", Constants.GRANT_TYPE_AUTHORIZATION_CODE)
        assertEquals("challenge", Constants.CODE_VERIFIER_CHALLENGE)

        assertEquals("code", Constants.SERVER_PARAMETER_CODE)
        assertEquals("error", Constants.SERVER_PARAMETER_ERROR)

        assertEquals("error_host_lookup", Constants.ERROR_HOST_LOOKUP_TOKEN)
        assertEquals("access_denied", Constants.ERROR_ACCESS_DENIED)

        assertEquals("users.read", Constants.SCOPE_USERS_READ)
        assertEquals("tweet.read", Constants.SCOPE_TWEET_READ)
        assertEquals("tweet.write", Constants.SCOPE_TWEET_WRITE)
        assertEquals("offline.access", Constants.SCOPE_OFFLINE_ACCESS)
        assertEquals("list.read", Constants.SCOPE_LIST_READ)
        assertEquals("follows.read", Constants.SCOPE_FOLLOWS_READ)
        assertEquals("like.read", Constants.SCOPE_LIKE_READ)
        assertEquals("like.write", Constants.SCOPE_LIKE_WRITE)
        assertEquals("space.read", Constants.SCOPE_SPACE_READ)
    }

    @Test
    fun compObj() {
        val c = Constants()
        assertEquals(true, c is Constants)
    }
}