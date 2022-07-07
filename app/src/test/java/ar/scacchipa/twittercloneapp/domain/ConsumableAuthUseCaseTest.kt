package ar.scacchipa.twittercloneapp.domain

import org.junit.Assert
import org.junit.Test

class ConsumableAuthUseCaseTest {
    @Test
    fun consumableAuthUseCaseShouldReturnArl() {
        val consumableAuthUseCase = ConsumableAuthUseCase()
        Assert.assertEquals(consumableAuthUseCase(),
            "https://twitter.com/i/oauth2/authorize?" +
                    "response_type=code&" +
                    "client_id=Yzg1a01Hcm16RTdKdmptZmhJdEs6MTpjaQ&" +
                    "redirect_uri=https://twittercloneendava.firebaseapp.com/__/auth/handler&" +
                    "scope=users.read%20tweet.read%20tweet.write%20offline.access%20" +
                    "list.read%20follows.read%20like.read%20like.write%20space.read&" +
                    "state=state&" +
                    "code_challenge=challenge&" +
                    "code_challenge_method=plain"
            )
    }
}
