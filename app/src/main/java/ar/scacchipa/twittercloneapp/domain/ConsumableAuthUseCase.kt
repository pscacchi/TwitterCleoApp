package ar.scacchipa.twittercloneapp.domain

import ar.scacchipa.twittercloneapp.repository.DbContants

open class ConsumableAuthUseCase {

    open operator fun invoke(clientId: String, redirectUri: String): String {
        return "https://twitter.com/i/oauth2/authorize?" +
                "response_type=${DbContants.RESPONSE_TYPE_CODE}&" +
                "client_id=$clientId&" +
                "redirect_uri=$redirectUri&" +
                "scope=${getScope()}&" +
                "state=${DbContants.STATE_STATE}&" +
                "code_challenge=${DbContants.CODE_CHALLENGE_CHALLENGE}&" +
                "code_challenge_method=${DbContants.CODE_CHALLENGE_METHOD_PLAIN}"
    }

    private fun getScope():String {
        return listOf(
            DbContants.SCOPE_USERS_READ,
            DbContants.SCOPE_TWEET_READ,
            DbContants.SCOPE_TWEET_WRTIE,
            DbContants.SCOPE_OFFLINE_ACCESS,
            DbContants.SCOPE_LIST_READ,
            DbContants.SCOPE_FOLLOWS_READ,
            DbContants.SCOPE_LIKE_READ,
            DbContants.SCOPE_LIKE_WRITE,
            DbContants.SCOPE_SPACE_READ
        ).joinToString("%20")
    }
}