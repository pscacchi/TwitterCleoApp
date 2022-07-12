package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.repository.Constants

open class ConsumableAuthUseCase {
    open operator fun invoke(): String {
        return "https://twitter.com/i/oauth2/authorize?" +
                "response_type=${Constants.RESPONSE_TYPE_CODE}&" +
                "client_id=${Constants.CLIENT_ID}&" +
                "redirect_uri=${Constants.REDIRECT_URI}&" +
                "scope=${getScope()}&" +
                "state=${Constants.STATE_STATE}&" +
                "code_challenge=${Constants.CODE_CHALLENGE_CHALLENGE}&" +
                "code_challenge_method=${Constants.CODE_CHALLENGE_METHOD_PLAIN}"
    }

    private fun getScope():String {
        return listOf(
            Constants.SCOPE_USERS_READ,
            Constants.SCOPE_TWEET_READ,
            Constants.SCOPE_TWEET_WRITE,
            Constants.SCOPE_OFFLINE_ACCESS,
            Constants.SCOPE_LIST_READ,
            Constants.SCOPE_FOLLOWS_READ,
            Constants.SCOPE_LIKE_READ,
            Constants.SCOPE_LIKE_WRITE,
            Constants.SCOPE_SPACE_READ
        ).joinToString("%20")
    }
}